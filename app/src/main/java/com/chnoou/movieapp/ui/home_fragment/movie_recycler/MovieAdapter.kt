package com.chnoou.movieapp.ui.home_fragment.movie_recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chnoou.movieapp.R
import com.chnoou.movieapp.backend.data.Movie

class MovieAdapter(private var list: List<Movie>): RecyclerView.Adapter<MovieViewHolder>() {

    var onClick: ((Movie) -> Unit)? = null

    fun submitList(list: List<Movie>) {
        val oldList = this.list
        val diffResult = DiffUtil.calculateDiff(MovieDiffCallback(oldList, list))
        this.list = list
        diffResult.dispatchUpdatesTo(this)
        /*if (oldList.isEmpty() || list.isEmpty()) {
            this.notifyDataSetChanged()
        }*/
    }

    class MovieDiffCallback(private var oldList: List<Movie>, private var newList: List<Movie>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }


}