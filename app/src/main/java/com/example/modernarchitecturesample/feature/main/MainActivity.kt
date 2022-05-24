package com.example.modernarchitecturesample.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.modernarchitecturesample.core.DataProvider
import com.example.modernarchitecturesample.core.repository.model.Movie
import com.example.modernarchitecturesample.databinding.ActivityMainBinding
import com.example.modernarchitecturesample.feature.detail.DetailActivity
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MainAdapter.MovieAdapterListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = MainViewModelFactory(DataProvider.provideRepository())
        viewModel = ViewModelProvider(
            owner = this,
            factory = viewModelFactory
        ).get(MainViewModel::class.java)

        with(binding) {
            lifecycleOwner = this@MainActivity
            mainMovieViewModel = viewModel
            rvMovie.adapter = MainAdapter(this@MainActivity)
        }

        observeErrorState(binding)
    }


    private fun observeErrorState(binding: ActivityMainBinding) {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            if (it.errorMessage.isNotEmpty()) {
                Snackbar.make(
                    this@MainActivity,
                    binding.root,
                    it.errorMessage,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onClicked(data: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}




