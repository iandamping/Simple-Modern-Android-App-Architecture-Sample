package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.datasource.RemoteDataSource
import com.example.modernarchitecturesample.core.repository.model.*
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteDataSource
) : MovieRepository {

    override suspend fun getRemoteMovie(): Results<List<Movie>> {
        return try {
            val result = remoteSource.getMovie().mapListMovieToRepository()
            Results.Success(result)
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Error")
        }
    }


    override suspend fun getDetailMovie(movieId: Int): Results<MovieDetail> {
        return try {
            val result = remoteSource.getDetailMovie(movieId).mapDetailMovieToRepository()
            Results.Success(result)
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Error")
        }
    }
}