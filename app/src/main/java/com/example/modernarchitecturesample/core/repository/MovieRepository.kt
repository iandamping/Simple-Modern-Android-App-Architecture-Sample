package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.repository.model.Movie
import com.example.modernarchitecturesample.core.repository.model.MovieDetail
import com.example.modernarchitecturesample.core.repository.model.Results
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getRemoteMovie(): Results<List<Movie>>

    val getCacheMovie: Flow<Results<List<Movie>>>

    suspend fun getDetailMovie(movieId: Int): Results<MovieDetail>
}