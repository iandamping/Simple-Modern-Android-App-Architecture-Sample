package com.example.modernarchitecturesample.feature.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.modernarchitecturesample.MainApplication
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.databinding.ActivityMainBinding
import com.example.modernarchitecturesample.feature.detail.DetailActivity
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as MainApplication).dataProvider.provideRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            lifecycleOwner = this@MainActivity
            mainMovieViewModel = viewModel
            rvMovie.adapter = MovieAdapter(this@MainActivity)
        }

    }

    override fun onClicked(data: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}




