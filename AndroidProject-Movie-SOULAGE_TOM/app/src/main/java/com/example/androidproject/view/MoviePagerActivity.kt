package com.example.androidproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidproject.R
import com.example.androidproject.ViewMovieContract
import com.example.androidproject.model.ViewMovieModelImpl
import com.example.androidproject.presenter.MOVIE_POSITION
import com.example.androidproject.presenter.ViewMoviePresenterImpl
import kotlinx.android.synthetic.main.activity_movie_pager.*

class MoviePagerActivity : AppCompatActivity(), ViewMovieContract.MovieView {

    private lateinit var presenter: ViewMovieContract.ViewMoviePresenter
    private  var initialPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_pager)

        handleInitialPosition(intent)
        initPresenter()
        handleBackMenu()
    }

    private fun handleBackMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPresenter() {
        presenter = ViewMoviePresenterImpl(this,this,ViewMovieModelImpl(this))
        presenter.requestToLoadMovies()
    }

    private fun handleInitialPosition(intent: Intent?) {
        initialPosition = intent?.getIntExtra(MOVIE_POSITION,0)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showMovie() {
        if(viewPager.adapter == null){
            viewPager.adapter = MoviePagerAdapter(presenter)
            viewPager.currentItem = initialPosition ?: 0
        }else{
            viewPager.adapter!!.notifyDataSetChanged()
        }
    }
}