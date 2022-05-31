package com.example.modernarchitecturesample.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modernarchitecturesample.MainApplication
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.feature.detail.DetailActivity
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var mainConstraintLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private val movieAdapter: MovieAdapter = MovieAdapter(this)

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MainApplication).dataProvider.provideRepository())
    }


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
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data.isNotEmpty() -> {
                    movieAdapter.submitList(it.data)
                }

                it.errorMessage.isNotEmpty() -> Snackbar.make(
                    this@MainActivity,
                    mainConstraintLayout,
                    it.errorMessage,
                    Snackbar.LENGTH_SHORT
                ).show()

            }

            if (it.isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onClicked(data: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}




