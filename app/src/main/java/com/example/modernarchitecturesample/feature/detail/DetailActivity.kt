package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import coil.load
import com.example.modernarchitecturesample.MainApplication
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(
            this,
            (application as MainApplication).dataProvider.provideRepository(),
            intent.extras
        )
    }

    private lateinit var detailConstraintLayout: ConstraintLayout
    private lateinit var detailImageView: ImageView
    private lateinit var favoriteImageView: ImageView
    private lateinit var tvTittle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        observeUiState()
    }


    private fun initView() {
        progressBar = findViewById(R.id.detail_progress_circular)
        detailConstraintLayout = findViewById(R.id.clDetail)
        detailImageView = findViewById(R.id.ivDetail)
        favoriteImageView = findViewById(R.id.ivBookmark)
        tvTittle = findViewById(R.id.tvTittle)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun observeUiState() {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {
                    tvDescription.text = it.data.overview
                    tvTittle.text = it.data.title
                    detailImageView.load(it.data.backdropPath) {
                        placeholder(R.drawable.ic_placeholder)
                        error(R.drawable.ic_error)
                    }
                }
                it.errorMessage.isNotEmpty() -> {
                    Snackbar.make(
                        this@DetailActivity,
                        detailConstraintLayout,
                        it.errorMessage,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            with(favoriteImageView) {
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

            progressBar.isVisible = it.isLoading
        }
    }


}