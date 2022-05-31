package com.example.modernarchitecturesample

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.modernarchitecturesample.local.FavoriteMovieEntity
import com.example.modernarchitecturesample.local.MovieEntity
import com.example.modernarchitecturesample.model.MovieDetailResponse
import com.example.modernarchitecturesample.network.ApiInterface
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

class DetailActivity : AppCompatActivity() {
    private val imageFormatter = "https://image.tmdb.org/t/p/w500"
    private lateinit var detailConstraintLayout: ConstraintLayout
    private lateinit var detailImageView: ImageView
    private var favoriteItem: MovieDetail? = null
    private lateinit var tvTittle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var progressBar: ProgressBar

    private var favoriteItemId: Int? = null
    private var isFavorite: Boolean = false
    private var favoriteItem: FavoriteMovieEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initView()
        getDetailMovie(intent.getIntExtra("movie_id", 0))
    }


    private fun initView() {
        progressBar = findViewById(R.id.detail_progress_circular)
        detailConstraintLayout = findViewById(R.id.clDetail)
        detailImageView = findViewById(R.id.ivDetail)
        tvTittle = findViewById(R.id.tvTittle)
        tvDescription = findViewById(R.id.tvDescription)
    }

    private fun deleteItem(movieId: Int) {
        lifecycleScope.launch {
            (application as MainApplication).database.favMovieDao().deleteFavoriteMovieById(movieId)
        }
    }

    private fun setFavoriteItem(data: FavoriteMovieEntity) {
        lifecycleScope.launch {
            (application as MainApplication).database.favMovieDao().insertFavoriteMovie(data)
        }
    }

    private fun provideApiInterface(): ApiInterface {
        val baseUrl = "https://api.themoviedb.org/3/"
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val httpClient = OkHttpClient.Builder()
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
        return Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
    }


    private fun getDetailMovie(movieId: Int) {
        progressBar.visibility = View.VISIBLE

        provideApiInterface().getDetailMovie(movieId = movieId)
            .enqueue(object : Callback<MovieDetailResponse> {
                override fun onResponse(
                    call: Call<MovieDetailResponse>,
                    response: Response<MovieDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (progressBar.isVisible){
                            progressBar.visibility = View.GONE
                        }
                        val data = response.body()
                        if (data != null) {
                            tvDescription.text = data.overview
                            tvTittle.text = data.title
                            detailImageView.load(imageFormatter + data.backdropPath) {
                                placeholder(R.drawable.ic_placeholder)
                                error(R.drawable.ic_error)
                            }


                            lifecycleScope.launch {
                                (application as MainApplication).database.favMovieDao().loadFavoriteMovie().collect{ favorite ->
                                    val data = favorite.firstOrNull { cached -> cached.movieId == movieId }
                                    if (data == null) {
                                        isFavorite = false
                                        favoriteItem = response
                                        favoriteImageView.load(R.drawable.ic_unbookmark)
                                    } else {
                                        isFavorite = true
                                        favoriteItemId = data.localId
                                        favoriteItem = response
                                        favoriteImageView.load(R.drawable.ic_bookmarked)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MovieDetailResponse>,
                    t: Throwable
                ) {
                    if (progressBar.isVisible){
                        progressBar.visibility = View.GONE
                    }
                    Snackbar.make(
                        this@DetailActivity,
                        detailConstraintLayout,
                        t.localizedMessage ?: "Error",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            })
    }
}