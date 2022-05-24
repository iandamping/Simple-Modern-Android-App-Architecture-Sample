package com.example.modernarchitecturesample.core.datasource.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainResponse<T>(
    @Json(name = "results") val results: List<T>
)