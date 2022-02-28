package com.example.androidproject.view

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.MoviesContract
import com.example.androidproject.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

private const val TITLE_SIZE = 45

class MoviesAdapter(private val presenter: MoviesContract.MoviesPresenter) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), MoviesContract.SingleMovieView{

        private val ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val viewForeground : LinearLayout = itemView.findViewById(R.id.viewForeground)

        override fun setTitle(title: String) {
            val finalTitle = if (title.length > TITLE_SIZE) title.substring(0, TITLE_SIZE-43) + "..." else title
            tvTitle.text = finalTitle
        }

        override fun setImage(path: String) {
            Picasso.get()
                    .load(File(path))
                    .transform(RoundedCornersTransformation(50,5))
                    .into(ivImage)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_movie,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setOnClickListener { presenter.view(position)}
        presenter.bindSingleMovie(holder,position)
    }

    override fun getItemCount()= presenter.moviesCount

}