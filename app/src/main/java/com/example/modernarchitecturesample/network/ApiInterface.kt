package com.example.modernarchitecturesample.network

import com.example.modernarchitecturesample.model.MovieDetailResponse
import com.example.modernarchitecturesample.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET(popularMovie)
    fun getPopularMovie(@Query("api_key") apiKey: String = api_key): Call<MainResponse<MovieResponse>>

    @GET("$detailMovie{movie}")
    fun getDetailMovie(
        @Path("movie") movieId: Int,
        @Query("api_key") apiKey: String  = api_key
    ): Call<MainResponse<MovieDetailResponse>>


    companion object{
        const val popularMovie = "movie/popular"
        const val detailMovie = "movie/"
        const val api_key = "9986464dc7ff8e83569e65a98dc7b3b6"
    }
}