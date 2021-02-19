package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

class AuthTokenByUuidRequest(
    @field:NotBlank
    @field:Pattern(regexp = "LOGIN")
    val type: String,

    @field:NotBlank
    val uuid: String,

    @field:NotBlank
    val token: String
) : Serializable
