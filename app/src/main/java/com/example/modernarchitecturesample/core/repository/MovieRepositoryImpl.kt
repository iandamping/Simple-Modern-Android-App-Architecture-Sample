package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.datasource.cache.CacheDataSource
import com.example.modernarchitecturesample.core.datasource.remote.RemoteDataSource
import com.example.modernarchitecturesample.core.repository.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
) : MovieRepository {

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

    override suspend fun getRemoteMovie(): Results<List<Movie>> {
        return try {
            val result = remoteSource.getMovie().mapListMovieToRepository()
            Results.Success(result)
        } catch (e: Exception) {
            Results.Error(e.localizedMessage ?: "Error")
        }
    }

    override val getCacheMovie: Flow<Results<List<Movie>>>
        get() = cacheDataSource.loadMovie().map { cacheValue ->
            if (cacheValue.isEmpty()) {
                try {
                    val cacheData = remoteSource.getMovie()
                    cacheDataSource.insertMovie(*cacheData.mapListToDatabase().toTypedArray())
                    Results.Success(cacheData.mapListMovieToRepository())
                } catch (e: Exception) {
                    Results.Error(e.localizedMessage ?: "Error")
                }
            } else {
                if (cacheValue.first().timestamp.isExpired()) {
                    try {
                        val cacheData = remoteSource.getMovie()
                        cacheDataSource.deleteAllMovie()
                        cacheDataSource.insertMovie(*cacheData.mapListToDatabase().toTypedArray())
                        Results.Success(cacheData.mapListMovieToRepository())
                    } catch (e: Exception) {
                        Results.Error(e.localizedMessage ?: "Error")
                    }
                } else Results.Success(cacheValue.mapToListMovie())
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