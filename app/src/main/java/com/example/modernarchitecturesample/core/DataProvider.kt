package com.example.modernarchitecturesample.core

import android.content.Context
import com.example.modernarchitecturesample.core.datasource.local.FavoriteLocalDataSource
import com.example.modernarchitecturesample.core.datasource.local.FavoriteLocalDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSource
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.local.db.DatabaseProvider
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkProvider.provideApiInterface
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkProvider.provideMoshi
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkProvider.provideOkHTTPClient
import com.example.modernarchitecturesample.core.datasource.network.rest.NetworkUtilProvider
import com.example.modernarchitecturesample.core.repository.MovieRepository
import com.example.modernarchitecturesample.core.repository.MovieRepositoryImpl

class DataProvider(private val context: Context) {

    private fun provideRemoteDatasource(): RemoteDataSource {
        return RemoteDataSourceImpl(
            provideApiInterface(
                okHttpClient = provideOkHTTPClient(),
                moshi = provideMoshi()
            )
        )
    }

    private fun provideCacheDataSource(): LocalDataSource {
        return LocalDataSourceImpl(DatabaseProvider(context).provideMovieDatabase().movieDao())
    }

    private fun provideFavoriteCacheDataSource(): FavoriteLocalDataSource {
        return FavoriteLocalDataSourceImpl(
            DatabaseProvider(context).provideMovieDatabase().favMovieDao()
        )
    }

    fun provideRepository(): MovieRepository {
        return MovieRepositoryImpl(
            remoteSource = provideRemoteDatasource(),
            localSource = provideCacheDataSource(),
            favoriteLocalSource = provideFavoriteCacheDataSource(),
            networkUtils = NetworkUtilProvider(context).provideNetworkUtil()
        )
    }
}