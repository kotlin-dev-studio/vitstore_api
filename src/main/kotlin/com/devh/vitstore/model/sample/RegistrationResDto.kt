package com.devh.vitstore.model.sample

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RegistrationResDto(
    val activeToken: String,
    val type: String
)
