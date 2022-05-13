package com.example.modernarchitecturesample.core.datasource.cache

import kotlinx.coroutines.flow.Flow

interface CacheDataSource {

    fun loadMovie(): Flow<List<MovieEntity>>

    suspend fun insertMovie(vararg data: MovieEntity)

    suspend fun deleteMovieById(selectedId: Int)

    suspend fun deleteAllMovie()
}