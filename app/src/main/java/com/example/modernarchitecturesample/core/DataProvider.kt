package com.example.modernarchitecturesample.core

import android.content.Context
import com.example.modernarchitecturesample.core.datasource.cache.CacheDataSource
import com.example.modernarchitecturesample.core.datasource.cache.CacheDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.cache.db.DatabaseProvider
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkProvider
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkUtilProvider
import com.example.modernarchitecturesample.core.repository.MovieRepository
import com.example.modernarchitecturesample.core.repository.MovieRepositoryImpl

class DataProvider(private val context: Context) {

    private fun provideRemoteDatasource(): RemoteDataSource {
        return RemoteDataSourceImpl(NetworkProvider.provideApiInterface())
    }

    private fun provideCacheDataSource(): CacheDataSource {
        return CacheDataSourceImpl(DatabaseProvider(context).provideMovieDatabase().movieDao())
    }

    fun provideRepository(): MovieRepository {
        return MovieRepositoryImpl(
            remoteSource = provideRemoteDatasource(),
            cacheSource = provideCacheDataSource(),
            networkUtils = NetworkUtilProvider(context).provideNetworkUtil()
        )
    }
}