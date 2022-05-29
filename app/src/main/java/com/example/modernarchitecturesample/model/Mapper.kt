package com.example.modernarchitecturesample.model

import com.example.modernarchitecturesample.local.MovieEntity


fun MovieResponse.mapToDatabase(): MovieEntity = MovieEntity(
    id = null,
    movieId = id,
    voteAverage = voteAverage,
    title = title,
    poster_path = "https://image.tmdb.org/t/p/w500$poster_path",
    originalTitle = originalTitle,
    backdrop_path = backdrop_path,
    overview = overview,
    voteCount = vote_count,
    video = video,
    popularity = popularity,
    originalLanguage = original_language,
    adult = adult,
    releaseDate = release_date,
    timestamp = System.currentTimeMillis()
)

fun List<MovieResponse>.mapListToDatabase() = map { it.mapToDatabase() }
