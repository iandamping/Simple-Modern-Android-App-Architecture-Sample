package com.example.modernarchitecturesample.core.repository.model

sealed class Results<out R> {
    data class Success<out T>(val data: T) : Results<T>()
    data class Error(val message: String) : Results<Nothing>()
}
