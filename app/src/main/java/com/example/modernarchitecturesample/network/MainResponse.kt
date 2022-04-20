package com.example.modernarchitecturesample.network

import com.squareup.moshi.Json

data class MainResponse<T>(
    @field:Json(name = "results") val results: List<T>
)