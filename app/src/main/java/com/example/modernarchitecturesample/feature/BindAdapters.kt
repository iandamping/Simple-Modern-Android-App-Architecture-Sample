package com.example.modernarchitecturesample.feature

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.R
import com.example.modernarchitecturesample.feature.main.MovieAdapter
import com.example.modernarchitecturesample.feature.main.MainMovieState

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
    @BindingAdapter("detailProgressState")
    fun bindDetailProgressBar(view: ProgressBar, data: Boolean) {
        if (!data) {
            if (view.isVisible) {
                view.visibility = View.GONE
            }
        } else view.visibility = View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindImage(imgView: ImageView, imgUrl: String) {
        if (imgUrl.isNotEmpty()) {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            imgView.load(imgUri) {
                placeholder(R.drawable.ic_placeholder)
                error(R.drawable.ic_error)
            }
        }
    }

}