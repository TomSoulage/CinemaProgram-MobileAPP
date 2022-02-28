package com.example.androidproject

import android.view.View
import com.example.androidproject.dao.Movie

interface ViewMovieContract {

    interface ViewMovieModel {
        interface OnMoviesLoadedListener {
            fun onMoviesLoaded(movies: List<Movie>)
        }
        interface OnMovieUpdatedListener{
            fun onMovieUpdated(movie: Movie)
        }
        fun loadMovies(listener: OnMoviesLoadedListener)
        fun updateMovie(movie: Movie,listener: OnMovieUpdatedListener)

    }

    interface MovieView {
        fun showMovie()
    }

    interface  SingleMovieView {
        fun setTitle(title: String)
        fun setDescription(description: String)
        fun setImage(image: String)
        fun setDateTime(dateTime : String)
        fun setRead(read: Boolean)
    }

    interface ViewMoviePresenter {
        fun requestToLoadMovies()
        val moviesCount : Int

        fun bindSingleMovie(singleMovieView: SingleMovieView, position: Int)
        fun toggleReadStatus(position: Int)
        fun onDestroy()
    }

}