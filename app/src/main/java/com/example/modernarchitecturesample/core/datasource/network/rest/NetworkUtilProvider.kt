package com.example.modernarchitecturesample.core.datasource.network.rest

import android.content.Context
import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtils
import com.example.modernarchitecturesample.core.datasource.network.util.NetworkUtilsImpl

class NetworkUtilProvider(private val context: Context) {

    fun provideNetworkUtil(): NetworkUtils {
        return NetworkUtilsImpl(context)
    }
}