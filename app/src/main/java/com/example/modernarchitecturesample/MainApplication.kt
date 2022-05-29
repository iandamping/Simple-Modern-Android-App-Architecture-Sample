package com.example.modernarchitecturesample

import android.app.Application
import com.example.modernarchitecturesample.local.MovieDatabase
import timber.log.Timber

class MainApplication : Application() {

    val database:MovieDatabase by lazy { MovieDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}