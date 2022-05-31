package com.example.modernarchitecturesample.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.datasource.model.MovieDetail
import com.example.modernarchitecturesample.core.repository.MovieRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MovieRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailMovieState> =
        MutableStateFlow(DetailMovieState.initialize())
    val uiState: StateFlow<DetailMovieState> = _uiState.asStateFlow()


//    fun getMovie(movieId: Int) {
//        viewModelScope.launch {
//            repository.getSingleFavoriteCacheMovie(movieId).catch {
//                _uiState.update { currentUiState ->
//                    currentUiState.copy(
//                        isLoading = false,
//                        errorMessage = it.localizedMessage
//                    )
//                }
//            }.collect { data ->
//                _uiState.update { currentUiState ->
//                    currentUiState.copy(isLoading = false, data = data)
//                }
//            }
//        }
//    }

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
                repository.getSingleFavoriteCacheMovie(it).catch {
                    _uiState.update { currentUiState ->
                        currentUiState.copy(
                            isLoading = false,
                            errorMessage = it.localizedMessage
                        )
                    }
                }.collect { data ->
                    _uiState.update { currentUiState ->
                        currentUiState.copy(isLoading = false, data = data)
                    }
                }
            }
        }
    }

}