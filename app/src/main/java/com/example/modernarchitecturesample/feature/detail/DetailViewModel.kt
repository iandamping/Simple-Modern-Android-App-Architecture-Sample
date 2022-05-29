package com.example.modernarchitecturesample.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailMovieState> =
        MutableStateFlow(DetailMovieState.initialize())
    val uiState: StateFlow<DetailMovieState> = _uiState.asStateFlow()

    private val _movieId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val movieId: StateFlow<Int?> = _movieId.asStateFlow()

    fun setMovieId(movieId: Int?) {
        _movieId.value = movieId
    }


    init {
        viewModelScope.launch {
            movieId.collect { movieId ->
                if (movieId != null) {
                    try {
                        val data = repository.getDetailMovie(movieId)
                        _uiState.update { currentUiState ->
                            currentUiState.copy(isLoading = false, data = data)
                        }
                    } catch (e: Exception) {
                        _uiState.update { currentUiState ->
                            currentUiState.copy(
                                isLoading = false,
                                errorMessage = e.localizedMessage
                            )
                        }
                    }
                }
            }
        }
    }

}