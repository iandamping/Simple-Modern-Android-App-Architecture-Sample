package com.example.modernarchitecturesample.core.datasource.cache.db

import android.content.Context

class DatabaseProvider(private val context: Context) {

    fun provideMovieDatabase(): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }
}