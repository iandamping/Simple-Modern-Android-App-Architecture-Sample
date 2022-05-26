package com.example.modernarchitecturesample.core.datasource.network

import com.example.modernarchitecturesample.core.datasource.network.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MovieResponse
import com.example.modernarchitecturesample.core.datasource.network.rest.ApiInterface

class RemoteDataSourceImpl(private val api: ApiInterface) : RemoteDataSource {

    override suspend fun getMovie(): List<MovieResponse> {
        try {
            val response = api.getPopularMovie()
            return if (response.isSuccessful) {
                val body = response.body()?.results
                if (!body.isNullOrEmpty()) {
                    body
                } else {
                    throw Exception("No result available")
                }
            } else throw Exception("Application encounter problem with the server")

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDetailMovie(movieId: Int): MovieDetailResponse {
        try {
            val response = api.getDetailMovie(movieId)
            return if (response.isSuccessful) {
                response.body() ?: throw Exception("No result available")
            } else throw Exception("Application encounter problem with the server")

        } catch (e: Exception) {
            throw e
        }
    }
}