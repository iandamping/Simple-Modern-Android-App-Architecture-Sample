package com.example.modernarchitecturesample.feature.detail

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.modernarchitecturesample.core.repository.MovieRepository

class DetailViewModelFactory(
    owner: SavedStateRegistryOwner,
    private val repository: MovieRepository,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return DetailViewModel(
            repository, handle
        ) as T
    }
}