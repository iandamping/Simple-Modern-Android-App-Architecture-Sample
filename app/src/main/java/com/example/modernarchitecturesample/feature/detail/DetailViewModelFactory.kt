package com.example.modernarchitecturesample.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.modernarchitecturesample.core.repository.MovieRepository

class DetailViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}