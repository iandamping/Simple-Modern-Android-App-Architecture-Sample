package com.example.modernarchitecturesample.core.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_primary_id") val id: Int?,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster_path") val poster_path: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "backdrop_path") val backdrop_path: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "movie_time_stamp") val timestamp: Long
)