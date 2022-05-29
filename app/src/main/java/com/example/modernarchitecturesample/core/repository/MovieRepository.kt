package com.example.modernarchitecturesample.core.repository


import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.example.modernarchitecturesample.core.datasource.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    val getCacheMovie: Flow<List<Movie>>

    val getFavoriteCacheMovie: Flow<List<MovieDetail>>

    suspend fun getDetailMovie(movieId: Int): MovieDetail

    suspend fun setFavorite(data: MovieDetail)

    suspend fun deleteFavorite(id: Int)
}