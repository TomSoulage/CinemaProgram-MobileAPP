package com.example.androidproject

import android.app.Application
import com.example.androidproject.dao.MovieDao
import com.example.androidproject.dao.MovieDatabase

class App : Application() {

    private  lateinit var movieDao : MovieDao

    override fun onCreate() {
        super.onCreate()
        var db = MovieDatabase.getInstance(this)
        movieDao = db.movieDao()
    }
    fun getMovieDao() = movieDao
}