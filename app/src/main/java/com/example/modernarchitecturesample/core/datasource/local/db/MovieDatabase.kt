package com.example.modernarchitecturesample.core.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.modernarchitecturesample.core.datasource.local.model.FavoriteMovieEntity
import com.example.modernarchitecturesample.core.datasource.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class, FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun favMovieDao(): FavoriteMovieDao

}
