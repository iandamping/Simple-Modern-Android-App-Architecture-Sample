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
                        ivBookmark.setOnClickListener { _ ->
                            if (it.data.localId != null) {
                                viewModel.removeFavoriteMovie(it.data.localId)
                            } else viewModel.setFavoriteMovie(it.data)
                        }

                        ivDetail.load(it.data.backdropPath) {
                            placeholder(R.drawable.ic_placeholder)
                            error(R.drawable.ic_error)
                        }
                        ivBookmark.load(
                            if (it.data.localId != null) {
                                R.drawable.ic_bookmarked
                            } else R.drawable.ic_unbookmark

                        )
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
            binding.detailProgressCircular.isVisible = it.isLoading
        }
    }
}