package com.example.modernarchitecturesample.core.datasource

import com.example.modernarchitecturesample.core.datasource.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.model.MovieResponse

interface RemoteDataSource {
    suspend fun getMovie(): List<MovieResponse>

    suspend fun getDetailMovie(movieId: Int): MovieDetailResponse

}