package com.devh.vitstore.common.dto

sealed class ResultDataRes<out T> {
    data class Success<T>(val data: T? = null) : ResultDataRes<T>()
    data class Failure<T>(val error: Exception) : ResultDataRes<T>()
}
