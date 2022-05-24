package com.example.modernarchitecturesample.core.repository.model

import com.example.modernarchitecturesample.core.datasource.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.model.MovieResponse
import com.example.modernarchitecturesample.core.datasource.network.ApiInterface.Companion.imageFormatter

fun MovieResponse.mapMovieToRepository(): Movie = Movie(
    id = id, title = title, poster_path = imageFormatter + poster_path, overview = overview
)

fun MovieDetailResponse.mapDetailMovieToRepository(): MovieDetail = MovieDetail(
    backdropPath = imageFormatter + backdropPath,
    overview = overview,
    posterPath = imageFormatter + posterPath,
    title = title
)

fun List<MovieResponse>.mapListMovieToRepository() = map { it.mapMovieToRepository() }
