package com.example.modernarchitecturesample.feature.detail

import com.example.modernarchitecturesample.core.repository.model.MovieDetail

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