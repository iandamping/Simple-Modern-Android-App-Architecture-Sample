package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.datasource.cache.CacheDataSource
import com.example.modernarchitecturesample.core.datasource.model.*
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtils
import com.example.modernarchitecturesample.core.repository.model.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class MovieRepositoryImpl(
    private val remoteSource: RemoteDataSource,
    private val cacheSource: CacheDataSource,
    private val networkUtils: NetworkUtils
) : MovieRepository {

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

    override val getCacheMovie: Flow<Results<List<Movie>>>
        get() = cacheSource.loadMovie().map { cacheValue ->
            if (cacheValue.isEmpty()) {
                if (!networkUtils.hasNetworkConnection()) {
                    Results.Error("No internet connection")
                } else {
                    val cacheData = remoteSource.getMovie()
                    cacheSource.insertMovie(*cacheData.mapListToDatabase().toTypedArray())
                    Results.Success(cacheData.mapListMovieToRepository())
                }
            } else {
                if (!networkUtils.hasNetworkConnection()) {
                    Results.Success(cacheValue.mapToListMovie())
                } else {
                    if (cacheValue.first().timestamp.isExpired()) {
                        val cacheData = remoteSource.getMovie()

                        cacheSource.deleteAllMovie()
                        cacheSource.insertMovie(
                            *cacheData.mapListToDatabase().toTypedArray()
                        )
                        Results.Success(cacheData.mapListMovieToRepository())
                    } else Results.Success(cacheValue.mapToListMovie())
                }

            }
        }.catch {
            Results.Error(it.localizedMessage)
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