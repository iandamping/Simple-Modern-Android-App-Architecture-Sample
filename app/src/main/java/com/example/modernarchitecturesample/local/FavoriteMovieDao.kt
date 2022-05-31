package com.example.modernarchitecturesample.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM fav_movie_table")
    fun loadFavoriteMovie(): Flow<List<FavoriteMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(data: FavoriteMovieEntity)

    @Query("DELETE FROM fav_movie_table where movie_primary_id = :selectedId")
    suspend fun deleteFavoriteMovieById(selectedId: Int)

    @Query("DELETE FROM fav_movie_table")
    suspend fun deleteAllFavoriteMovie()
}