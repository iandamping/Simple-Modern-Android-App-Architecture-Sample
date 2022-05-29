package com.example.modernarchitecturesample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.databinding.ItemMovieBinding


class MovieAdapter(
    private val listener: MovieAdapterListener
) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(listMoviedapterCallback) {

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
            with(binding) {
                ivMovieImage.load(data.poster_path) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                }
                tvMovieName.text = data.title
                tvMovieReleaseDate.text = data.overview
            }
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

