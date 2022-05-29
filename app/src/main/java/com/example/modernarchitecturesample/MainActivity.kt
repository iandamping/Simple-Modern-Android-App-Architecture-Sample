package com.example.modernarchitecturesample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modernarchitecturesample.core.datasource.model.Movie
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), MovieAdapter.MovieAdapterListener {
    private lateinit var movieRecyclerView: RecyclerView
    private lateinit var mainConstraintLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar
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


    private fun getMovie() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            (application as MainApplication).dataProvider.provideRepository().getCacheMovie.catch {
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
                Snackbar.make(
                    this@MainActivity,
                    mainConstraintLayout,
                    it.localizedMessage,
                    Snackbar.LENGTH_SHORT
                ).show()
            }.collect { result ->
                if (progressBar.isVisible) {
                    progressBar.visibility = View.GONE
                }
                movieAdapter.submitList(result)
            }
        }

    }

    override fun onClicked(data: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movie_id", data.id)
        startActivity(intent)
    }

}


