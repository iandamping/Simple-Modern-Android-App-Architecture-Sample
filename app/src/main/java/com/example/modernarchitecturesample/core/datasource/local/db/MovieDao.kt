package com.example.modernarchitecturesample.core.datasource.local.db

import androidx.room.*
import com.example.modernarchitecturesample.core.datasource.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun loadMovie(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg data: MovieEntity)

    @Transaction
    suspend fun insertAndDeleteMovie(vararg data: MovieEntity){
        deleteAllMovie()
        insertMovie(*data)
    }

    @Query("DELETE FROM movie_table where movie_primary_id = :selectedId")
    suspend fun deleteMovieById(selectedId: Int)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovie()
}