package com.example.wmart.ui

sealed interface ResultState<out T: Any> {
    sealed class Success<out T: Any>: ResultState<T> {
        data class NonEmpty<out T: Any>(val value: T): Success<T>()
        object Empty : Success<Nothing>()
    }

    data class Failure(val ex: Throwable): ResultState<Nothing>

    object Loading: ResultState<Nothing>
}