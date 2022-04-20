package com.example.modernarchitecturesample.model

import com.squareup.moshi.Json

data class MovieResponse(
    @field:Json(name = "id") val id: Int?,
    @field:Json(name = "vote_average") val voteAverage: Double?,
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "poster_path") val poster_path: String?,
    @field:Json(name = "original_title") val originalTitle: String?,
    @field:Json(name = "backdrop_path") val backdrop_path: String?,
    @field:Json(name = "overview") val overview: String?
)