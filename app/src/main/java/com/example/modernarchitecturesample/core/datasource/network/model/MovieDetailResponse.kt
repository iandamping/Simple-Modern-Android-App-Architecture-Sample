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
    @Json(name = "vote_average") val voteAverage: Double
)

