package com.example.modernarchitecturesample.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.databinding.ActivityMainBinding
import com.example.modernarchitecturesample.feature.detail.DetailActivity
import com.example.modernarchitecturesample.feature.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            lifecycleOwner = this@MainActivity
            mainMovieViewModel = viewModel
            rvMovie.adapter = MovieAdapter(this@MainActivity)
        }
        observeState()
    }

    private fun observeState() {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            if (it.errorMessage.isNotEmpty()) {
                if (::binding.isInitialized) {
                    Snackbar.make(
                        this@MainActivity,
                        binding.root,
                        it.errorMessage,
                        Snackbar.LENGTH_SHORT
                    ).show()
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



