package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.core.DataProvider
import com.example.modernarchitecturesample.databinding.ActivityDetailBinding
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelFactory = DetailViewModelFactory(DataProvider.provideRepository())
        viewModel = ViewModelProvider(
            owner = this,
            factory = viewModelFactory
        ).get(DetailViewModel::class.java)

        viewModel.setMovieId(intent.getIntExtra("movie_id", 0))

        observeState(binding)
    }



    private fun observeState(binding: ActivityDetailBinding) {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {
                    with(binding){
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
            if (!it.isLoading) {
                if (binding.detailProgressCircular.isVisible) {
                    binding.detailProgressCircular.visibility = View.GONE
                }
            } else binding.detailProgressCircular.visibility = View.VISIBLE
        }
    }
}