package com.example.modernarchitecturesample.core.datasource.model

data class MovieDetail(
    val localId:Int?,
    val movieId:Int,
    val backdropPath: String,
    val overview: String,
    val title: String,
)
