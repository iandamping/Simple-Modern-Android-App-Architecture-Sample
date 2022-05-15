package com.example.modernarchitecturesample.feature.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.example.modernarchitecturesample.feature.util.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkUtilsImpl @Inject constructor(@ApplicationContext val context: Context) :
    NetworkUtils {

    override fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}