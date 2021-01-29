package com.devh.vitstore.common.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class ResultListRes<out T>(
    val results: List<T>? = null,
    val totalItems: Long? = null,
    val currentPage: Int? = null,
    val totalPages: Int? = null,
    val hasNext: Boolean = false
)
