package com.example.modernarchitecturesample.core.datasource.network.rest

import com.example.modernarchitecturesample.core.datasource.network.model.MovieDetailResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MovieResponse
import com.example.modernarchitecturesample.core.datasource.network.model.MainResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET(popularMovie)
    suspend fun getPopularMovie(@Query("api_key") apiKey: String = api_key): Response<MainResponse<MovieResponse>>

    @GET("$detailMovie{movie}")
    suspend fun getDetailMovie(
        @Path("movie") movieId: Int,
        @Query("api_key") apiKey: String  = api_key
    ): Response<MovieDetailResponse>


    companion object{
        const val baseUrl = "https://api.themoviedb.org/3/"
        const val popularMovie = "movie/popular"
        const val detailMovie = "movie/"
        const val api_key = "9986464dc7ff8e83569e65a98dc7b3b6"
        const val imageFormatter = "https://image.tmdb.org/t/p/w500"
    }
}