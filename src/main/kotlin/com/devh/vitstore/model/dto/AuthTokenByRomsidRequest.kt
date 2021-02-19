package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class AuthTokenByRomsidRequest(
    val romsid: String?,

    @field:NotBlank
    @field:Pattern(regexp = "^(REGISTRATION|PWCHANGE)$")
    val type: String,

    val token: String?
) : Serializable
