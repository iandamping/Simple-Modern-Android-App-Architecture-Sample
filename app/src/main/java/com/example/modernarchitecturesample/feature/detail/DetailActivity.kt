package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import coil.load
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.databinding.ActivityDetailBinding
import com.example.modernarchitecturesample.feature.launchAndCollectIn
import com.example.modernarchitecturesample.feature.main.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            lifecycleOwner = this@DetailActivity
            detailViewModel = viewModel
        }
        observeState(binding)
    }

    private fun observeState(binding: ActivityDetailBinding) {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {
                    binding.ivBookmark.setOnClickListener { _ ->
                        if (it.data.localId != null) {
                            viewModel.removeFavoriteMovie(it.data.localId)
                        } else viewModel.setFavoriteMovie(it.data)
                    }

                }
            }
        }
    }
}