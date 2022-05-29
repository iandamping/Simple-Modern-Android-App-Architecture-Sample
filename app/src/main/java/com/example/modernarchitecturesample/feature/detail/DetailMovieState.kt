package com.example.modernarchitecturesample.feature.detail

import com.example.modernarchitecturesample.core.datasource.model.MovieDetail


data class DetailMovieState(
    val isLoading: Boolean,
    val data: MovieDetail?,
    val errorMessage: String
) {
    companion object {
        fun initialize() = DetailMovieState(
            isLoading = true,
            data = null,
            errorMessage = ""
        )
    }
}