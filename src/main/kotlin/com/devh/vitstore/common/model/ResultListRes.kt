package com.devh.vitstore.common.model

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.data.domain.Page

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class ResultListRes<out T>(
    private val pageProducts: Page<T>,
    val results: List<T> = pageProducts.content,
    val totalItems: Long = pageProducts.totalElements,
    val currentPage: Int = pageProducts.number,
    val totalPages: Int = pageProducts.totalPages,
    val hasNext: Boolean = pageProducts.hasNext()
)
