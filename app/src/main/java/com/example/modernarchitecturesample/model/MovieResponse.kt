package com.example.modernarchitecturesample.model

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
    @Json(name = "overview") val overview: String,
    @Json(name = "vote_count") val vote_count: Int,
    @Json(name = "video") val video: Boolean,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "original_language") val original_language: String,
    @Json(name = "genre_ids") val genre_ids: List<Int>,
    @Json(name = "adult") val adult: Boolean,
    @Json(name = "release_date") val release_date: String
)