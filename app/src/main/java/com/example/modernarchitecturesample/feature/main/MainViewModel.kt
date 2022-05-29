package com.example.modernarchitecturesample.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<MainMovieState> =
        MutableStateFlow(MainMovieState.initialize())
    val uiState: StateFlow<MainMovieState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getCacheMovie.catch {
                _uiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false, errorMessage = it.localizedMessage)
                }
            }.collect { data ->
                _uiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false, data = data)
                }
            }

        }
    }
}