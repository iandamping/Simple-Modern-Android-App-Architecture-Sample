package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.modernarchitecturesample.MainApplication
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.core.DataProvider
import com.example.modernarchitecturesample.util.launchAndCollectIn
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(this,(application as MainApplication).dataProvider.provideRepository(),intent.extras)
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
        getDetailMovie()
    }


    private fun initView() {
        progressBar = findViewById(R.id.detail_progress_circular)
        detailConstraintLayout = findViewById(R.id.clDetail)
        detailImageView = findViewById(R.id.ivDetail)
        favoriteImageView = findViewById(R.id.ivBookmark)
        tvTittle = findViewById(R.id.tvTittle)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun getDetailMovie() {
        viewModel.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            when {
                it.data != null -> {

                    favoriteImageView.setOnClickListener { _ ->
                        if (it.data.localId != null) {
                            viewModel.removeFavoriteMovie(it.data.localId)
                        } else viewModel.setFavoriteMovie(it.data)
                    }

                    favoriteImageView.load(
                        if (it.data.localId != null) {
                            R.drawable.ic_bookmarked
                        } else R.drawable.ic_unbookmark

                    )

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

            if (it.isLoading){
                progressBar.visibility = View.VISIBLE
            }else{
                progressBar.visibility = View.GONE
            }
        }
    }
}