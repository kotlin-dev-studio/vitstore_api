package com.devh.vitstore.model.dto

import com.devh.vitstore.model.enum.TokenType
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class ChangePasswordRequest(
    @field:NotBlank
    val activeToken: String,

    @field:NotBlank
    @field:Pattern(regexp = "PWCHANGE")
    val type: String = TokenType.PWCHANGE.name,

    @field:NotBlank
    val password: String
) : Serializable
