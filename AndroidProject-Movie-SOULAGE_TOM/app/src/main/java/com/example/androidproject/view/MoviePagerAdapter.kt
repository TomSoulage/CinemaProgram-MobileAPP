package com.example.androidproject.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.R
import com.example.androidproject.ViewMovieContract
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.list_movie.view.*
import java.io.File

class MoviePagerAdapter(private val presenter: ViewMovieContract.ViewMoviePresenter) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ViewMovieContract.SingleMovieView {

        private val ivMovie = itemView.findViewById<ImageView>(R.id.ivMovie)
        val ivRead = itemView.findViewById<ImageView>(R.id.ivRead)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        private val tvDate = itemView.findViewById<TextView>(R.id.tvDate)



        override fun setTitle(title: String) {
            tvTitle.text = title
        }

        override fun setDescription(description: String) {
            tvDescription.text = description
        }

        override fun setImage(image: String) {
            Picasso.get()
                .load(File(image))
                .transform(RoundedCornersTransformation(50,5))

                .into(ivMovie)        }

        override fun setDateTime(dateTime: String) {
            tvDate.text  = dateTime
        }

        override fun setRead(read: Boolean) {
            ivRead.setImageResource(if (read) R.drawable.visibility else R.drawable.visibility_off)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_movie,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivRead.setOnClickListener {
            presenter.toggleReadStatus(position)
            notifyItemChanged(position)
        }
        presenter.bindSingleMovie(holder,position)
    }

    override fun getItemCount() = presenter.moviesCount


}