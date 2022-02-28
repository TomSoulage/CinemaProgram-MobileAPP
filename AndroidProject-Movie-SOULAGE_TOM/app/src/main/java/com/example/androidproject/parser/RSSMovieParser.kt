package com.example.androidproject.parser

import android.content.Context
import android.nfc.Tag
import com.example.androidproject.dao.Movie
import com.example.androidproject.factory.createGetHttpUrlConnection
import com.example.androidproject.factory.createXMLPullParser
import downloadImageAndStore
import org.xmlpull.v1.XmlPullParser
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=1"
private const val FILE_PREFIX = "MOVIE_"

fun parse(context: Context) : List<Movie> {
    var movies= mutableListOf<Movie>()
    var con = createGetHttpUrlConnection(RSS_URL)

    con.inputStream.use {

        var parser = createXMLPullParser(it)
        var event = parser.eventType
        var tagType: TagType? = null
        var movie: Movie? = null


        while (event != XmlPullParser.END_DOCUMENT) {
            when (event) {
                XmlPullParser.START_TAG -> {
                    var name = parser.name
                    tagType = try {
                        TagType.of(name)
                    } catch (e: IllegalArgumentException) {
                        null
                    }
                }
                XmlPullParser.TEXT -> {
                    if (tagType != null) {
                        val text = parser.text.trim()
                        when (tagType) {
                            TagType.ITEM -> {
                                movie = Movie()
                                movies.add(movie)
                            }
                            TagType.TITLE -> {
                                if (movie != null && text.isNotBlank()) {
                                    movie.title = text
                                }
                            }
                            TagType.DESCRIPTION -> {
                                if (movie != null && text.isNotBlank()) {
                                    var descriptionString = filterDescription(text)
                                    movie.description = descriptionString
                                }
                            }
                            TagType.PUBDATE -> {
                                if (movie != null && text.isNotBlank()) {
                                    movie.publishedDateTime =
                                            LocalDateTime.parse(text, DateTimeFormatter.RFC_1123_DATE_TIME)
                                }
                            }
                            TagType.PLAKAT -> {
                                if (movie != null && text.isNotBlank()) {
                                    val url = text
                                    if (url != null) {
                                        val picturePath = downloadImageAndStore(
                                                context, url, FILE_PREFIX + movie.title.hashCode())
                                        if (picturePath != null) {
                                            movie.picturePath = picturePath
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            event = parser.next()
        }
    }

    return movies
}

private enum class TagType(val value: String){
    ITEM("item"),
    TITLE("title"),
    DESCRIPTION("description"),
    PUBDATE("pubDate"),
    PLAKAT("plakat");
    companion object{
        fun of(value: String) = valueOf(value.toUpperCase())
    }
}

fun filterDescription(string: String): String {
    var text = ""
    var i = 0;
    while(i<string.length){
        if(string[i] == '<'){
            while(string[i]!='>'){
                i += 1
            }
        }else{
            text += string[i]

        }
        i += 1
    }
    return text
}