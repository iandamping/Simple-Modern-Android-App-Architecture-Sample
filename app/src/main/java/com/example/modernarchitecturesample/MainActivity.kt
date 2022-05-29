package com.example.modernarchitecturesample

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modernarchitecturesample.local.MovieEntity
import com.example.modernarchitecturesample.model.MovieResponse
import com.example.modernarchitecturesample.model.mapListToDatabase
import com.example.modernarchitecturesample.network.ApiInterface
import com.example.modernarchitecturesample.network.MainResponse
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit



class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var mainConstraintLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar
    private val movieAdapter: MovieAdapter = MovieAdapter(this)
    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        observeCacheMovie()
    }

    private fun initView() {
        progressBar = findViewById(R.id.main_progress_circular)
        mainConstraintLayout = findViewById(R.id.clMain)
        movieRecyclerView = findViewById(R.id.rvMovie)
        movieRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
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


    private fun getMovie() {
        progressBar.visibility = View.VISIBLE

        provideApiInterface().getPopularMovie()
            .enqueue(object : Callback<MainResponse<MovieResponse>> {
                override fun onResponse(
                    call: Call<MainResponse<MovieResponse>>,
                    response: Response<MainResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful) {
                        if (progressBar.isVisible) {
                            progressBar.visibility = View.GONE
                        }
                        val data = response.body()?.results
                        if (!data.isNullOrEmpty()) {
                            lifecycleScope.launch {
                                (application as MainApplication).database.movieDao()
                                    .insertAndDeleteMovie(*data.mapListToDatabase().toTypedArray())
                            }
                        } else {
                            Snackbar.make(
                                this@MainActivity,
                                mainConstraintLayout,
                                "No result available",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            this@MainActivity,
                            mainConstraintLayout,
                            "Application encounter problem with the server",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MainResponse<MovieResponse>>, t: Throwable) {
                    if (progressBar.isVisible) {
                        progressBar.visibility = View.GONE
                    }

                    Snackbar.make(
                        this@MainActivity,
                        mainConstraintLayout,
                        t.localizedMessage,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun observeCacheMovie(){
        lifecycleScope.launch {
            (application as MainApplication).database.movieDao().loadMovie().collect{
                if (it.isEmpty()) {
                    getMovie()
                } else {
                    if (hasNetworkConnection()){
                        if (it.first().timestamp.isExpired()){
                            getMovie()
                        } else movieAdapter.submitList(it)
                    }else movieAdapter.submitList(it)
                }
            }
        }
    }

    override fun onClicked(data: MovieEntity) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.movieId)
        startActivity(intent)
    }

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }

}





