package com.example.androidproject.presenter

import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import com.example.androidproject.MoviesContract
import com.example.androidproject.dao.Movie
import com.example.androidproject.utils.startActivity
import com.example.androidproject.view.MoviePagerActivity

const val MOVIE_POSITION = "com.example.androidproject.presenter.movie_position"

class MoviesPresenterImpl(val context: Context?, private var view : MoviesContract.ListMoviesView?,private var model: MoviesContract.MoviesModel) : MoviesContract.MoviesPresenter, MoviesContract.MoviesModel.OnMoviesLoadedListener,MoviesContract.MoviesModel.OnMovieDeletedListener {

    private val movies = mutableListOf<Movie>()

    override fun requestToLoadMovies() {
        view?.showProgress()
        model.loadMovies(this)
    }
    override fun onMoviesLoaded(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        view?.showMovies()
    }
    override val moviesCount: Int
        get() = movies.size

    override fun bindSingleMovie(singleMovieView: MoviesContract.SingleMovieView, position: Int) {
        val movie = movies[position]
       movie.title?.let { singleMovieView.setTitle(it) }
        movie.picturePath?.let { singleMovieView.setImage(it) }

    }

    override fun delete(position: Int) {
        val movie = movies[position]
        model.delete(movie,this)
    }

    override fun onMovieDeleted(movie: Movie) {
        movies.remove(movie)
        view?.showMovies()
    }
    override fun view(position: Int) {
        context?.startActivity<MoviePagerActivity>(MOVIE_POSITION, position)
    }

    override fun onDestroy() {
        view = null
    }



}