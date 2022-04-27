package com.example.modernarchitecturesample.core.repository

import com.example.modernarchitecturesample.core.repository.model.Movie
import com.example.modernarchitecturesample.core.repository.model.MovieDetail
import com.example.modernarchitecturesample.core.repository.model.Results

interface MovieRepository {

    suspend fun getMovie(): Results<List<Movie>>

    suspend fun getDetailMovie(movieId: Int): Results<MovieDetail>
}