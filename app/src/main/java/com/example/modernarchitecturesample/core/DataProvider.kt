package com.example.modernarchitecturesample.core

import com.example.modernarchitecturesample.core.datasource.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.RemoteDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.network.NetworkProvider
import com.example.modernarchitecturesample.core.repository.MovieRepository
import com.example.modernarchitecturesample.core.repository.MovieRepositoryImpl

object DataProvider {

    private fun provideRemoteDatasource(): RemoteDataSource {
        return RemoteDataSourceImpl(NetworkProvider.provideApiInterface())
    }

    fun provideRepository(): MovieRepository {
        return MovieRepositoryImpl(provideRemoteDatasource())
    }
}