package com.devh.vitstore.model.dto

import com.devh.vitstore.model.enum.TokenType
import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ActiveTokenDto(
    val activeToken: String,
    val type: TokenType? = null
) : Serializable
