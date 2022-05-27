package com.example.modernarchitecturesample.core

import android.content.Context
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSource
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.local.db.DatabaseProvider
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

    private fun provideCacheDataSource(): LocalDataSource {
        return LocalDataSourceImpl(DatabaseProvider(context).provideMovieDatabase().movieDao())
    }

    fun provideRepository(): MovieRepository {
        return MovieRepositoryImpl(
            remoteSource = provideRemoteDatasource(),
            cacheSource = provideCacheDataSource(),
            networkUtils = NetworkUtilProvider(context).provideNetworkUtil()
        )
    }
}