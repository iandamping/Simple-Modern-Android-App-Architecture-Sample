package com.example.modernarchitecturesample.di

import com.example.modernarchitecturesample.core.datasource.remote.RemoteDataSource
import com.example.modernarchitecturesample.core.datasource.remote.RemoteDataSourceImpl
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
    fun provideRemoteDataSource(source:RemoteDataSourceImpl):RemoteDataSource
}