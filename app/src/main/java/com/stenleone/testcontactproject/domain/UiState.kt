package com.stenleone.testcontactproject.domain

sealed class UiState {
    class Success<T>(val data: T) : UiState()
    object Loading: UiState()
    class Error(val error: ParsedError)
}