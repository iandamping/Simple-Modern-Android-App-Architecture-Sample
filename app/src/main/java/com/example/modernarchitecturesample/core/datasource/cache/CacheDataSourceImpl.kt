package com.example.modernarchitecturesample.core.datasource.cache

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CacheDataSourceImpl @Inject constructor(private val dao: MovieDao) : CacheDataSource {
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