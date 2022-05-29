package com.example.modernarchitecturesample.core.datasource.network

import com.example.modernarchitecturesample.core.datasource.network.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MovieResponse
import com.example.modernarchitecturesample.core.datasource.network.rest.ApiInterface
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface) : RemoteDataSource {

    override suspend fun getMovie(): List<MovieResponse> {
        val response = api.getPopularMovie()
        return if (response.isSuccessful) {
            val body = response.body()?.results
            if (!body.isNullOrEmpty()) {
                body
            } else {
                throw Exception("No result available")
            }
        } else throw Exception("Application encounter problem with the server")
    }

    override suspend fun getDetailMovie(movieId: Int): MovieDetailResponse {
        val response = api.getDetailMovie(movieId)
        return if (response.isSuccessful) {
            response.body() ?: throw Exception("No result available")
        } else throw Exception("Application encounter problem with the server")
    }
}