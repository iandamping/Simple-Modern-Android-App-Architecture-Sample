package com.example.modernarchitecturesample.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modernarchitecturesample.core.repository.MovieRepository
import com.example.modernarchitecturesample.core.repository.model.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainMovieViewModel @Inject constructor (private val movieRepository: MovieRepository) : ViewModel() {

    private val _uiState:MutableStateFlow<MainMovieState> = MutableStateFlow(MainMovieState.initialize())
    val uiState:StateFlow<MainMovieState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            when(val data = movieRepository.getMovie()){
                is Results.Error -> _uiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false, errorMessage = data.message)
                }
                is Results.Success -> _uiState.update { currentUiState ->
                    currentUiState.copy(isLoading = false, data = data.data)
                }
            }
        }
    }
}