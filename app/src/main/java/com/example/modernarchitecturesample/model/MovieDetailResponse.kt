package com.example.modernarchitecturesample.model

import com.squareup.moshi.Json

data class MovieDetailResponse(
    @field:Json(name = "backdrop_path") val backdropPath: String,
    @field:Json(name = "budget") val budget: Int,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "poster_path") val posterPath: String,
    @field:Json(name = "tagline") val tagline: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "vote_average") val voteAverage: Double
)

