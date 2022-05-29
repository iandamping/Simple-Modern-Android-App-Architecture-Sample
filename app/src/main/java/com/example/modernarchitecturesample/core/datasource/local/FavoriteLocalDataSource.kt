package com.example.modernarchitecturesample.core.datasource.local

import com.example.modernarchitecturesample.core.datasource.local.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {

    fun loadFavoriteMovie(): Flow<List<FavoriteMovieEntity>>

    suspend fun insertFavoriteMovie(data: FavoriteMovieEntity)

    suspend fun deleteFavoriteMovieById(selectedId: Int)

    suspend fun deleteAllFavoriteMovie()
}