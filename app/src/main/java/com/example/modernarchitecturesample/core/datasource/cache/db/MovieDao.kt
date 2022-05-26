package com.example.modernarchitecturesample.core.datasource.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.modernarchitecturesample.core.datasource.cache.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun loadMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg data: MovieEntity)

    @Query("DELETE FROM movie_table where movie_primary_id = :selectedId")
    suspend fun deleteMovieById(selectedId: Int)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovie()
}