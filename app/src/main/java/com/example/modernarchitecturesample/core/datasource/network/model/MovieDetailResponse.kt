package com.example.modernarchitecturesample.core.datasource.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailResponse(
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "budget") val budget: Int,
    @Json(name = "id") val id: Int,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "tagline") val tagline: String,
    @Json(name = "title") val title: String,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "imdb_id") val imdb_id: String,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "original_title") val original_title: String,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "release_date") val release_date: String,
    @Json(name = "revenue") val revenue: String,
    @Json(name = "runtime") val runtime: Int,
    @Json(name = "status") val status: String,
    @Json(name = "video") val video: Boolean,
    @Json(name = "vote_count") val vote_count: Int
)

