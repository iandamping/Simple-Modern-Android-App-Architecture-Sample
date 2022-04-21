package com.example.modernarchitecturesample.model

import com.squareup.moshi.Json

data class MovieDetailResponse(
    @Json(name = "backdrop_path") val backdropPath: String,
    @Json(name = "id") val id: Int,
    @Json(name = "overview") val overview: String,
    @Json(name = "poster_path") val posterPath: String,
    @Json(name = "title") val title: String,
)

