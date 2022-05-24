package com.example.modernarchitecturesample.core.datasource.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "vote_average") val voteAverage: Double,
    @Json(name = "title") val title: String,
    @Json(name = "poster_path") val poster_path: String,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "backdrop_path") val backdrop_path: String,
    @Json(name = "overview") val overview: String
)