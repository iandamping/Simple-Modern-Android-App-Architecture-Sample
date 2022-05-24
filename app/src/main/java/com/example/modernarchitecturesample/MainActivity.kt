package com.example.modernarchitecturesample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.modernarchitecturesample.databinding.ItemMovieBinding
import com.example.modernarchitecturesample.model.MovieResponse
import com.example.modernarchitecturesample.network.ApiInterface
import com.example.modernarchitecturesample.network.MainResponse
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
    private lateinit var progressBar:ProgressBar
    private val movieAdapter: MovieAdapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        getMovie()
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
                        if (progressBar.isVisible){
                            progressBar.visibility = View.GONE
                        }
                        val data = response.body()?.results
                        if (!data.isNullOrEmpty()) {
                            movieAdapter.submitList(data)
                        } else {
                            Snackbar.make(
                                this@MainActivity,
                                mainConstraintLayout,
                                "Error empty data",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<MainResponse<MovieResponse>>, t: Throwable) {
                    if (progressBar.isVisible){
                        progressBar.visibility = View.GONE
                    }

                    Snackbar.make(
                        this@MainActivity,
                        mainConstraintLayout,
                        t.localizedMessage ?: "Error",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            })
    }

    override fun onClicked(data: MovieResponse) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}

class MovieAdapter(
    private val listener: MovieAdapterListener
) :
    ListAdapter<MovieResponse, MovieAdapter.MovieViewHolder>(listMoviedapterCallback) {

    interface MovieAdapterListener {
        fun onClicked(data: MovieResponse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imageFormatter = "https://image.tmdb.org/t/p/w500"

        fun bind(data: MovieResponse) {
            with(binding) {
                ivMovieImage.load(imageFormatter + data.backdrop_path) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_error)
                }
                tvMovieName.text = data.title
                tvMovieReleaseDate.text = data.overview
            }
        }
    }

    companion object {
        val listMoviedapterCallback = object : DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
            ): Boolean {
                return oldItem.poster_path == newItem.poster_path
            }
        }
    }
}



