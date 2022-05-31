package com.example.modernarchitecturesample.core.datasource.local

import com.example.modernarchitecturesample.core.datasource.local.db.FavoriteMovieDao
import com.example.modernarchitecturesample.core.datasource.local.model.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

class FavoriteLocalDataSourceImpl(private val dao: FavoriteMovieDao) : FavoriteLocalDataSource {
    override fun loadFavoriteMovie(): Flow<List<FavoriteMovieEntity>> {
        return dao.loadFavoriteMovie()
    }

    override suspend fun insertFavoriteMovie(data: FavoriteMovieEntity) {
        dao.insertFavoriteMovie(data)
    }

    override suspend fun deleteFavoriteMovieById(selectedId: Int) {
       dao.deleteFavoriteMovieById(selectedId)
    }

    override suspend fun deleteAllFavoriteMovie() {
       dao.deleteAllFavoriteMovie()
    }
}