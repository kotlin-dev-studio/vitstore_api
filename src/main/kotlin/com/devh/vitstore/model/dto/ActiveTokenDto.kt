package com.devh.vitstore.model.dto

import com.devh.vitstore.model.enum.TokenType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class ActiveTokenDto(
    val activeToken: String,
    val type: TokenType? = null
) : Serializable
