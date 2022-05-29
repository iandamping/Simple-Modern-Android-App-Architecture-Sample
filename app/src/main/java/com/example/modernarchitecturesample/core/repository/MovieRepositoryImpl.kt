package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.datasource.local.LocalDataSource
import com.example.modernarchitecturesample.core.datasource.model.*
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

class MovieRepositoryImpl(
    private val remoteSource: RemoteDataSource,
    private val localSource: LocalDataSource,
    private val networkUtils: NetworkUtils
) : MovieRepository {

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

    override val getCacheMovie: Flow<List<Movie>>
        get() = localSource.loadMovie().map { cache ->
            if (cache.isEmpty()) {
                val remoteData = remoteSource.getMovie()
                localSource.insertMovie(*remoteData.mapListToDatabase().toTypedArray())
                remoteData.mapListMovieToRepository()
            } else {
                if (networkUtils.hasNetworkConnection()) {
                    if (cache.first().timestamp.isExpired()) {
                        val remoteData = remoteSource.getMovie()
                        localSource.insertAndDeleteMovie(
                            *remoteData.mapListToDatabase().toTypedArray()
                        )
                        remoteData.mapListMovieToRepository()
                    } else cache.mapToListMovie()
                } else cache.mapToListMovie()
            }
        }

    override suspend fun getDetailMovie(movieId: Int): MovieDetail {
        return remoteSource.getDetailMovie(movieId).mapDetailMovieToRepository()
    }

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }


}