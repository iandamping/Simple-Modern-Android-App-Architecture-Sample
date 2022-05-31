package com.example.modernarchitecturesample.core.datasource.model

import com.example.modernarchitecturesample.core.datasource.local.model.FavoriteMovieEntity
import com.example.modernarchitecturesample.core.datasource.local.model.MovieEntity
import com.example.modernarchitecturesample.core.datasource.network.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MovieResponse
import com.example.modernarchitecturesample.core.datasource.network.rest.ApiInterface.Companion.imageFormatter

fun MovieResponse.mapMovieToRepository(): Movie = Movie(
    id = id, title = title, poster_path = imageFormatter + poster_path, overview = overview
)

fun MovieDetailResponse.mapDetailMovieToRepository(): MovieDetail = MovieDetail(
    localId = null,
    movieId = id,
    backdropPath = imageFormatter + backdropPath,
    overview = overview,
    title = title
)

fun List<MovieResponse>.mapListMovieToRepository() = map { it.mapMovieToRepository() }


fun MovieResponse.mapToDatabase(): MovieEntity = MovieEntity(
    id = null,
    movieId = id,
    voteAverage = voteAverage,
    title = title,
    poster_path = imageFormatter + poster_path,
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

fun MovieEntity.mapToDomain(): Movie = Movie(
    id = movieId,
    title = title,
    poster_path = poster_path,
    overview = overview
)

fun List<MovieEntity>.mapToListMovie() = map { it.mapToDomain() }


fun MovieDetail.mapToDatabase(): FavoriteMovieEntity = FavoriteMovieEntity(
    id = null,
    favoriteMovieId = movieId,
    backdropPath = backdropPath,
    overview = overview,
    title = title
)

fun FavoriteMovieEntity.mapToUi(): MovieDetail = MovieDetail(
    localId = id,
    movieId = favoriteMovieId,
    backdropPath = backdropPath,
    overview = overview,
    title = title
)

fun List<FavoriteMovieEntity>.mapListToUi() = map { it.mapToUi() }
