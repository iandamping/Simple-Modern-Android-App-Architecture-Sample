package com.example.modernarchitecturesample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.databinding.ItemMovieBinding
import com.example.modernarchitecturesample.local.MovieEntity

class MovieAdapter(
    private val listener: MovieAdapterListener
) :
    ListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(listMoviedapterCallback) {

    interface MovieAdapterListener {
        fun onClicked(data: MovieEntity)
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

        private val imageFormatter = "https://image.tmdb.org/t/p/w500"

        fun bind(data: MovieEntity) {
            with(binding) {
                ivMovieImage.load(imageFormatter + data.backdrop_path) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                }
                tvMovieName.text = data.title
                tvMovieReleaseDate.text = data.overview
            }
        }
    }

    companion object {
        val listMoviedapterCallback = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return oldItem.poster_path == newItem.poster_path
            }
        }
    }
}