package com.example.modernarchitecturesample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.core.repository.model.Results
import com.example.modernarchitecturesample.databinding.ItemMovieBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var mainConstraintLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private val movieAdapter: MovieAdapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getMovie()
    }

    private fun initView() {
        progressBar = findViewById(R.id.main_progress_circular)
        mainConstraintLayout = findViewById(R.id.clMain)
        movieRecyclerView = findViewById(R.id.rvMovie)
        movieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }
    }


    private fun getMovie() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            (application as MainApplication).dataProvider.provideRepository().getCacheMovie.collect { result ->
                when (result) {
                    is Results.Error -> {
                        if (progressBar.isVisible) {
                            progressBar.visibility = View.GONE
                        }
                        Snackbar.make(
                            this@MainActivity,
                            mainConstraintLayout,
                            result.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    is Results.Success -> {
                        if (progressBar.isVisible) {
                            progressBar.visibility = View.GONE
                        }
                        movieAdapter.submitList(result.data)
                    }
                }
            }
        }

    }

    override fun onClicked(data: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}

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



