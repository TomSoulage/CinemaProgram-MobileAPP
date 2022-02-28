package com.example.androidproject.model

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.androidproject.App
import com.example.androidproject.MoviesContract
import com.example.androidproject.dao.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesModelImpl(val context : Context? ) : MoviesContract.MoviesModel {
    override fun loadMovies(listener: MoviesContract.MoviesModel.OnMoviesLoadedListener) {
        GlobalScope.launch {
            val movies = (context?.applicationContext as App).getMovieDao().getMovies()
            Handler(Looper.getMainLooper()).post{
                listener.onMoviesLoaded(movies)
            }
        }
    }

    override fun delete(movie: Movie, listener: MoviesContract.MoviesModel.OnMovieDeletedListener) {
        GlobalScope.launch {
            (context?.applicationContext as App).getMovieDao().getMovies()
            Handler(Looper.getMainLooper()).post{
                listener.onMovieDeleted(movie)
            }
        }
    }
}