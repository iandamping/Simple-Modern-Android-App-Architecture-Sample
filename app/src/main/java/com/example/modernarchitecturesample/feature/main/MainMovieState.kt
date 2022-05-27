package com.example.modernarchitecturesample.feature.main

import com.example.modernarchitecturesample.core.datasource.model.Movie


data class MainMovieState(
    val isLoading: Boolean,
    val data: List<Movie>,
    val errorMessage: String
) {
    companion object {
        fun initialize() = MainMovieState(
            isLoading = true,
            data = emptyList(),
            errorMessage = ""
        )
    }
}