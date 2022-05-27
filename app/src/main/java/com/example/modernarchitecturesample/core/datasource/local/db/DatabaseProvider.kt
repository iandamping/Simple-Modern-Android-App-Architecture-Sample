package com.example.modernarchitecturesample.core.datasource.local.db

import android.content.Context

class DatabaseProvider(private val context: Context) {

    fun provideMovieDatabase(): MovieDatabase {
        return MovieDatabase.getDatabase(context)
    }
}