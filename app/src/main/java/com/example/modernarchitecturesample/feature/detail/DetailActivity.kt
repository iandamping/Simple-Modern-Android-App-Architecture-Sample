package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.modernarchitecturesample.MainApplication
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.databinding.ActivityDetailBinding
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            this,
            (application as MainApplication).dataProvider.provideRepository(),
            intent.extras
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeState(binding)
    }


    private fun observeState(binding: ActivityDetailBinding) {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {
                    with(binding) {
                        ivDetail.load(it.data.backdropPath) {
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
            with(binding.ivBookmark) {
                load(
                    if (it.isBookmarked) R.drawable.ic_bookmarked else R.drawable.ic_unbookmark
                )

                setOnClickListener { _ ->
                    if (it.isBookmarked) {
                        if (it.data?.localId != null) {
                            viewModel.removeFavoriteMovie(it.data.localId)
                        }
                    } else if (it.data != null) viewModel.setFavoriteMovie(it.data)
                }
            }
            binding.detailProgressCircular.isVisible = it.isLoading
        }
    }
}