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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailMovieVm: DetailMovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailMovieVm.setMovieId(intent.getIntExtra("movie_id", 0))
        observeState(binding)
    }

    private fun observeState(binding: ActivityDetailBinding) {
        detailMovieVm.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {
                    val imgUri = it.data.posterPath.toUri().buildUpon().scheme("https").build()
                    with(binding){
                        ivDetail.load(imgUri) {
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_error)
                        }
                        tvTittle.text = it.data.title
                        tvDescription.text = it.data.overview
                    }

                }
                it.errorMessage.isNotEmpty() -> {
                    Snackbar.make(
                        this@DetailActivity,
                        binding.root,
                        it.errorMessage,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            if (!it.isLoading) {
                if (binding.detailProgressCircular.isVisible) {
                    binding.detailProgressCircular.visibility = View.GONE
                }
            } else binding.detailProgressCircular.visibility = View.VISIBLE
        }
    }
}