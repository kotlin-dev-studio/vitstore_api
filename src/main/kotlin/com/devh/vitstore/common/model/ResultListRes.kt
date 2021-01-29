package com.devh.vitstore.common.model

class ResultListRes<out T>(
    val result: List<T>? = null,
    val totalItems: Long? = null,
    val currentPage: Int? = null,
    val totalPages: Int? = null
)
