package com.example.modernarchitecturesample.core.datasource.local

import com.example.modernarchitecturesample.core.datasource.local.db.MovieDao
import com.example.modernarchitecturesample.core.datasource.local.model.MovieEntity
import kotlinx.coroutines.flow.Flow


class LocalDataSourceImpl(private val dao: MovieDao) : LocalDataSource {
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