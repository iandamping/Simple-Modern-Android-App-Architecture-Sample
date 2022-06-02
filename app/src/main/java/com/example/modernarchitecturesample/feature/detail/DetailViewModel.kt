package com.example.modernarchitecturesample.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.datasource.model.MovieDetail
import com.example.modernarchitecturesample.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

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