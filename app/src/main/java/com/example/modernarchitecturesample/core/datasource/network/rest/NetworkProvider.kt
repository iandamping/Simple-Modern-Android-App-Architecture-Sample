package com.example.modernarchitecturesample.core.datasource.network.rest

import com.example.modernarchitecturesample.core.datasource.network.rest.ApiInterface.Companion.baseUrl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NetworkProvider {

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    }

    fun provideOkHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .writeTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequests = 20
                maxRequestsPerHost = 20
            })
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                chain.run { proceed(this.request().newBuilder().build()) }
            }.build()
    }

    fun provideApiInterface(okHttpClient: OkHttpClient, moshi: Moshi): ApiInterface {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
    }
}