package com.mycompany.myapp.util.state

sealed class AsyncState<out T> {
    object Idle : AsyncState<Nothing>(), Incomplete
    object Loading : AsyncState<Nothing>(), Incomplete
    data class Success<out T>(val value: T) : AsyncState<T>()
    data class Fail<out T>(val throwable: Throwable) : AsyncState<T>()

    interface Incomplete
}