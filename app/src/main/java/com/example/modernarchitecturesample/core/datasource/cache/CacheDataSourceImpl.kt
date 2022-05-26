package com.example.modernarchitecturesample.core.datasource.cache

import com.example.modernarchitecturesample.core.datasource.cache.db.MovieDao
import com.example.modernarchitecturesample.core.datasource.cache.model.MovieEntity
import kotlinx.coroutines.flow.Flow


class CacheDataSourceImpl(private val dao: MovieDao) : CacheDataSource {
    override fun loadMovie(): Flow<List<MovieEntity>> {
        return dao.loadMovie()
    }

    override suspend fun insertMovie(vararg data: MovieEntity) {
        dao.insertMovie(*data)
    }

    override suspend fun deleteMovieById(selectedId: Int) {
        dao.deleteMovieById(selectedId)
    }

    override suspend fun deleteAllMovie() {
        dao.deleteAllMovie()
    }
}