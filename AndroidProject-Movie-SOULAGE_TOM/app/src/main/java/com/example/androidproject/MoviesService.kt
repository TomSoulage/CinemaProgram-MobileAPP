package com.example.androidproject


import com.example.androidproject.App
import com.example.androidproject.MoviesReceiver


import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.example.androidproject.parser.parse
import com.example.androidproject.utils.sendBroadcast

private const val JOB_ID = 1
class MoviesService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {

        val movies = parse(this)
        val dao = (applicationContext as App).getMovieDao()
        dao.insert(movies)
        //var moviesFromDB = dao.getMovies()
        //println()
        sendBroadcast<MoviesReceiver>()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent)
            = enqueueWork(context,MoviesService::class.java, JOB_ID,intent)
    }
}