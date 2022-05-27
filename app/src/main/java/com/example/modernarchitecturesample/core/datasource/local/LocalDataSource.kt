package com.example.modernarchitecturesample.core.datasource.local

import com.example.modernarchitecturesample.core.datasource.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun loadMovie(): Flow<List<MovieEntity>>

    suspend fun insertMovie(vararg data: MovieEntity)

    suspend fun deleteMovieById(selectedId: Int)

    suspend fun deleteAllMovie()
}