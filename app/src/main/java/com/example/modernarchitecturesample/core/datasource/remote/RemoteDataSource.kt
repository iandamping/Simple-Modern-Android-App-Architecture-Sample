package com.example.modernarchitecturesample.core.datasource.remote

import com.example.modernarchitecturesample.core.datasource.remote.network.response.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.remote.network.response.MovieResponse

interface RemoteDataSource {
    suspend fun getMovie():List<MovieResponse>

    suspend fun getDetailMovie(movieId:Int): MovieDetailResponse

}