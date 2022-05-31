package com.example.modernarchitecturesample.feature

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.feature.detail.DetailMovieState
import com.example.modernarchitecturesample.feature.detail.DetailViewModel
import com.example.modernarchitecturesample.feature.main.MainMovieState
import com.example.modernarchitecturesample.feature.main.MovieAdapter
import com.google.android.material.snackbar.Snackbar

object BindAdapters {
    @JvmStatic
    @BindingAdapter("listData")
    fun bindRecyclerView(recyclerView: RecyclerView, data: MainMovieState) {
        recyclerView.layoutManager =
            LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)
        val adapter = recyclerView.adapter as MovieAdapter
        if (data.data.isNotEmpty()) {
            adapter.submitList(data.data)
        }
    }

    @JvmStatic
    @BindingAdapter("mainProgressState")
    fun bindMainProgressBar(view: ProgressBar, data: MainMovieState) {
        view.isVisible = data.isLoading
    }

    @JvmStatic
    @BindingAdapter("snackBarMessage")
    fun bindSnackBar(view: ConstraintLayout, data: MainMovieState) {
        if (data.errorMessage.isNotEmpty()) {
            Snackbar.make(
                view.context,
                view,
                data.errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    @JvmStatic
    @BindingAdapter("detailProgressState")
    fun bindDetailProgressBar(view: ProgressBar, data: DetailMovieState) {
        view.isVisible = data.isLoading
    }

    @JvmStatic
    @BindingAdapter("detailSnackBarMessage")
    fun bindDetailSnackBar(view: ConstraintLayout, data: DetailMovieState) {
        if (data.errorMessage.isNotEmpty()) {
            Snackbar.make(
                view.context,
                view,
                data.errorMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    @JvmStatic
    @BindingAdapter("detailBookmarkImage")
    fun bindBookmarkImage(view: ImageView, data: DetailMovieState) {
        view.load(
            if (data.data?.localId != null) {
                R.drawable.ic_bookmarked
            } else R.drawable.ic_unbookmark
        )
    }


    @JvmStatic
    @BindingAdapter(value = ["app:detailState", "app:detailViewModel"], requireAll = true)
    fun bindBookmarkImageListener(view: ImageView, data: DetailMovieState,viewModel:DetailViewModel ) {
        view.setOnClickListener {
            if (data.data?.localId != null) {
                viewModel.removeFavoriteMovie(data.data.localId)
            } else if (data.data !=null) viewModel.setFavoriteMovie(data.data)
        }

    }

    @JvmStatic
    @BindingAdapter("detailTitleText")
    fun bindDetailTitleText(view: TextView, data: DetailMovieState) {
        view.text = data.data?.title
    }


    @JvmStatic
    @BindingAdapter("detailDescriptionText")
    fun bindDetailDescriptioonText(view: TextView, data: DetailMovieState) {
        view.text = data.data?.overview
    }

    @JvmStatic
    @BindingAdapter("loadDetailImage")
    fun bindDetailImage(imgView: ImageView, data: DetailMovieState) {
        val imgUri = data.data?.backdropPath?.toUri()?.buildUpon()?.scheme("https")?.build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindImage(imgView: ImageView, imgUrl: String) {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_error)
        }
    }

}