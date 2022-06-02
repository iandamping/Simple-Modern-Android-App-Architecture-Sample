package com.example.modernarchitecturesample.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.datasource.model.MovieDetail
import com.example.modernarchitecturesample.core.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetailMovieState> =
        MutableStateFlow(DetailMovieState.initialize())
    val uiState: StateFlow<DetailMovieState> = _uiState.asStateFlow()


    fun setFavoriteMovie(data: MovieDetail) {
        viewModelScope.launch {
            repository.setFavorite(data)
        }
    }

    fun removeFavoriteMovie(movieId: Int) {
        viewModelScope.launch {
            repository.deleteFavorite(movieId)
        }
    }

    init {
        viewModelScope.launch {
            savedStateHandle.get<Int>("movie_id")?.let {
                try {
                    val remoteData = repository.getDetailMovie(it)
                    repository.getSingleFavoriteCacheMovie(it).collect { cachedData ->
                        _uiState.update { currentUiState ->
                            currentUiState.copy(
                                data = cachedData ?: remoteData,
                                isLoading = false,
                                isBookmarked = cachedData != null
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(errorMessage = e.localizedMessage, isLoading = false)
                    }
                }
            }
        }
    }

}