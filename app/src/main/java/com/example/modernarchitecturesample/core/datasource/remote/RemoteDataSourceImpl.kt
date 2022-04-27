package com.example.modernarchitecturesample.core.datasource.remote

import com.example.modernarchitecturesample.core.datasource.remote.network.ApiInterface
import com.example.modernarchitecturesample.core.datasource.remote.network.response.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.remote.network.response.MovieResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ApiInterface): RemoteDataSource {

    override suspend fun getMovie(): List<MovieResponse> {
        try {
            val response = api.getPopularMovie()
            return if (response.isSuccessful) {
                val body = response.body()?.results
                if (!body.isNullOrEmpty()) {
                    body
                } else {
                    throw Exception("body is null")
                }
            } else throw Exception("call is not success")

        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getDetailMovie(movieId:Int): MovieDetailResponse {
        try {
            val response = api.getDetailMovie(movieId)
            return if (response.isSuccessful) {
                response.body() ?: throw Exception("body is null")
            } else throw Exception("call is not success")

        } catch (e: Exception) {
            throw e
        }
    }
}