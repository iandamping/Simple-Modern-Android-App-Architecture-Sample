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
import com.example.modernarchitecturesample.core.repository.model.Results
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var detailConstraintLayout: ConstraintLayout
    private lateinit var detailImageView: ImageView
    private lateinit var tvTittle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var progressBar: ProgressBar

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
        tvTittle = findViewById(R.id.tvTittle)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun getDetailMovie(movieId: Int) {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            when (val response = (application as MainApplication).dataProvider.provideRepository()
                .getDetailMovie(movieId)) {
                is Results.Error -> {
                    if (progressBar.isVisible) {
                        progressBar.visibility = View.GONE
                    }
                    Snackbar.make(
                        this@DetailActivity,
                        detailConstraintLayout,
                        response.message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is Results.Success -> {
                    if (progressBar.isVisible) {
                        progressBar.visibility = View.GONE
                    }
                    val data = response.data
                    tvDescription.text = data.overview
                    tvTittle.text = data.title
                    detailImageView.load(data.backdropPath) {
                        placeholder(R.drawable.ic_placeholder)
                        error(R.drawable.ic_error)
                    }
                }
            }
        }
    }
}