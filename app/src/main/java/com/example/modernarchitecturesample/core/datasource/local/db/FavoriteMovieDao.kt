package com.example.modernarchitecturesample.core.datasource.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.modernarchitecturesample.core.datasource.local.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM fav_movie_table")
    fun loadFavoriteMovie(): Flow<List<FavoriteMovieEntity>>

    @Query("SELECT * FROM fav_movie_table WHERE favorite_movie_id = :selectedId")
    fun loadSingleFavoriteMovie(selectedId: Int): Flow<FavoriteMovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(data: FavoriteMovieEntity)

    @Query("DELETE FROM fav_movie_table where movie_primary_id = :selectedId")
    suspend fun deleteFavoriteMovieById(selectedId: Int)

    @Query("DELETE FROM fav_movie_table")
    suspend fun deleteAllFavoriteMovie()
}