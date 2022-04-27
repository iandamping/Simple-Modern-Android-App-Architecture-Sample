package com.example.modernarchitecturesample.core.datasource.remote.network.response

import com.squareup.moshi.Json

data class MainResponse<T>(
    @Json(name = "results") val results: List<T>
)