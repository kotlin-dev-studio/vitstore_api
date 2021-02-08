package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ResendActiveTokenRequest(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp = "^(REGISTRATION|PWCHANGE)$")
    val type: String
) : Serializable
