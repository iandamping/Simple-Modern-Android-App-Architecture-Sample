package com.example.modernarchitecturesample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.modernarchitecturesample.core.datasource.model.MovieDetail
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var detailConstraintLayout: ConstraintLayout
    private lateinit var detailImageView: ImageView
    private lateinit var favoriteImageView: ImageView
    private lateinit var tvTittle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var progressBar: ProgressBar

    private var favoriteItemId: Int? = null
    private var isFavorite: Boolean = false
    private var favoriteItem: MovieDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        getDetailMovie(intent.getIntExtra("movie_id", 0))
    }


    private fun initView() {
        progressBar = findViewById(R.id.detail_progress_circular)
        detailConstraintLayout = findViewById(R.id.clDetail)
        detailImageView = findViewById(R.id.ivDetail)
        favoriteImageView = findViewById(R.id.ivBookmark)
        tvTittle = findViewById(R.id.tvTittle)
        tvDescription = findViewById(R.id.tvDescription)


        favoriteImageView.setOnClickListener {
            if (isFavorite) {
                if (favoriteItemId != null) deleteItem(checkNotNull(favoriteItemId))

            } else {
                if (favoriteItem != null) setFavoriteItem(checkNotNull(favoriteItem))

            }
        }
    }

    private fun deleteItem(movieId: Int) {
        lifecycleScope.launch {
            (application as MainApplication).dataProvider.provideRepository()
                .deleteFavorite(movieId)
        }
    }

    private fun setFavoriteItem(data: MovieDetail) {
        lifecycleScope.launch {
            (application as MainApplication).dataProvider.provideRepository()
                .setFavorite(data)
        }
    }

    private fun getDetailMovie(movieId: Int) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {

            try {
                val response = (application as MainApplication).dataProvider.provideRepository()
                    .getDetailMovie(movieId)
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
                tvDescription.text = response.overview
                tvTittle.text = response.title
                detailImageView.load(response.backdropPath) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                }
                (application as MainApplication).dataProvider.provideRepository().getFavoriteCacheMovie.collect { favorite ->
                    val data = favorite.firstOrNull { cached -> cached.movieId == movieId }
                    if (data == null) {
                        isFavorite = false
                        favoriteItem = response
                        favoriteImageView.load(R.drawable.ic_unbookmark)
                    } else {
                        isFavorite = true
                        favoriteItemId = data.localId
                        favoriteItem = response
                        favoriteImageView.load(R.drawable.ic_bookmarked)
                    }
                }

            } catch (e: Exception) {
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
                Snackbar.make(
                    this@DetailActivity,
                    detailConstraintLayout,
                    e.localizedMessage,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}