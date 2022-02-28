package com.example.androidproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidproject.MoviesContract
import com.example.androidproject.R
import com.example.androidproject.model.MoviesModelImpl
import com.example.androidproject.presenter.MoviesPresenterImpl
import kotlinx.android.synthetic.main.fragment_movies.*


class MoviesFragment : Fragment(), MoviesContract.ListMoviesView, MovieTouchHelperCallback.OnSwipedListener {

    private lateinit var presenter : MoviesContract.MoviesPresenter
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initRecycler()
        initTouchHelper()

    }

    private fun initTouchHelper() {
        MovieTouchHelperCallback(0, ItemTouchHelper.LEFT, this).apply {
            ItemTouchHelper(this).attachToRecyclerView(rvMovies)
        }
    }

    private fun initPresenter() {
        presenter = MoviesPresenterImpl(context,this,MoviesModelImpl(context))
        presenter.requestToLoadMovies()
    }

    private fun initRecycler() {
        rvMovies.layoutManager = LinearLayoutManager(context)
        adapter = MoviesAdapter(presenter)
        rvMovies.adapter = adapter
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showMovies() {
        progressBar.visibility = View.GONE
        adapter.notifyDataSetChanged()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSwiped(position: Int) {
        presenter.delete(position)
    }


}