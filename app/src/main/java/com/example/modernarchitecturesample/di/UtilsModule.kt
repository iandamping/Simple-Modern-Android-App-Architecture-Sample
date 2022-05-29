package com.example.modernarchitecturesample.di

import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtils
import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtilsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {

    @Binds
    fun bindsNetworkUtils(utils: NetworkUtilsImpl): NetworkUtils
}