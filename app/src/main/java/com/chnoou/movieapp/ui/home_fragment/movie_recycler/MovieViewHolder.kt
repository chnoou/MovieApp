package com.chnoou.movieapp.ui.home_fragment.movie_recycler

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.chnoou.movieapp.R
import com.chnoou.movieapp.backend.MovieAPI
import com.chnoou.movieapp.backend.data.Movie
import com.google.android.material.card.MaterialCardView

class MovieViewHolder(itemView: View, private val parent: ViewGroup): RecyclerView.ViewHolder(itemView) {

    private var imageCard: MaterialCardView? = null
    var image: ImageView? = null

    init {
        imageCard = itemView.findViewById(R.id.imageCard)
        image = itemView.findViewById(R.id.image)
    }

    fun bind(movie: Movie) {
        if (image != null) {
            val url = MovieAPI.BASE_MOVIE_IMAGE_URL + movie.poster_path
            Glide.with(image!!)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image!!)
        }
    }

}