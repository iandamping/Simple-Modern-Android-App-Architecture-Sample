package com.example.modernarchitecturesample

import android.app.Application
import com.example.modernarchitecturesample.core.DataProvider
import timber.log.Timber

class MainApplication : Application() {

    val dataProvider: DataProvider by lazy { DataProvider(this) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}