package com.example.modernarchitecturesample.di

import com.example.modernarchitecturesample.core.datasource.local.FavoriteLocalDataSource
import com.example.modernarchitecturesample.core.datasource.local.FavoriteLocalDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSource
import com.example.modernarchitecturesample.core.datasource.local.LocalDataSourceImpl
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.network.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun provideRemoteDataSource(source: RemoteDataSourceImpl): RemoteDataSource


    @Binds
    @Singleton
    fun provideFavoriteCacheDataSource(source: FavoriteLocalDataSourceImpl): FavoriteLocalDataSource

    @Binds
    @Singleton
    fun provideCacheDataSource(source: LocalDataSourceImpl): LocalDataSource
}