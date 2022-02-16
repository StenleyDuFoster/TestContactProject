package com.stenleone.testcontactproject.domain

sealed class DataState<T> {
    class Success<T>(val data: T) : DataState<T>()
    class Error<T>(val error: Throwable) : DataState<T>()
}