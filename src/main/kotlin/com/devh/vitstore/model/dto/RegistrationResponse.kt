package com.devh.vitstore.model.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.io.Serializable

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RegistrationResponse(
    val activeToken: String,
    val type: String
) : Serializable
