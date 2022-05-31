package com.example.modernarchitecturesample.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_movie_table")
data class FavoriteMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_primary_id") val id: Int?,
    @ColumnInfo(name = "favorite_movie_id") val favoriteMovieId: Int,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "title") val title: String,
)