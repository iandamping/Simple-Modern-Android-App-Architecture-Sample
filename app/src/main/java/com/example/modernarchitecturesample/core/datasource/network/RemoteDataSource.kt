package com.example.modernarchitecturesample.core.datasource.network

import com.example.modernarchitecturesample.core.datasource.network.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MovieResponse

interface RemoteDataSource {
    suspend fun getMovie(): List<MovieResponse>

    suspend fun getDetailMovie(movieId: Int): MovieDetailResponse

}