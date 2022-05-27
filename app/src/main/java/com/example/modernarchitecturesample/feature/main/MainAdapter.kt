package com.example.modernarchitecturesample.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.databinding.ItemMovieBinding

class MainAdapter(
    private val listener: MovieAdapterListener
) :
    ListAdapter<Movie, MainAdapter.MovieViewHolder>(listMoviedapterCallback) {

    interface MovieAdapterListener {
        fun onClicked(data: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Movie) {
            binding.movie = data
            binding.executePendingBindings()
        }
    }

    companion object {
        val listMoviedapterCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.poster_path == newItem.poster_path
            }
        }
    }
}