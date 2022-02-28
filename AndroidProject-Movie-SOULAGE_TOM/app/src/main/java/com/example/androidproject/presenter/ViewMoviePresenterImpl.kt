package com.example.androidproject.presenter

import android.content.Context
import android.view.View
import com.example.androidproject.ViewMovieContract
import com.example.androidproject.dao.Movie
import java.time.format.DateTimeFormatter

class ViewMoviePresenterImpl(
    private val context: Context?,
    private var view: ViewMovieContract.MovieView?,
    private val model: ViewMovieContract.ViewMovieModel
) : ViewMovieContract.ViewMoviePresenter, ViewMovieContract.ViewMovieModel.OnMoviesLoadedListener, ViewMovieContract.ViewMovieModel.OnMovieUpdatedListener {

    private val movies = mutableListOf<Movie>()

    override fun requestToLoadMovies() {
        model.loadMovies(this)
    }

    override fun onMoviesLoaded(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        view?.showMovie()
    }

    override val moviesCount: Int
        get() = movies.size

    override fun bindSingleMovie(
        singleMovieView: ViewMovieContract.SingleMovieView,
        position: Int
    ) {
        val movie = movies[position]
        movie.title?.let { singleMovieView.setTitle(it) }
        movie.picturePath?.let { singleMovieView.setImage(it) }
        movie.description?.let { singleMovieView.setDescription(it) }
        movie.publishedDateTime?.let { singleMovieView.setDateTime(it.format(DateTimeFormatter.ISO_DATE)) }
        movie.read?.let { singleMovieView.setRead(it) }
    }

    override fun toggleReadStatus(position: Int) {
        val movie = movies[position]
        movie.read = movie.read?.not() ?: true
        model.updateMovie(movie,this)
    }


    override fun onMovieUpdated(movie: Movie) {
        view?.showMovie()
    }

    override fun onDestroy() {
        view = null
    }



}