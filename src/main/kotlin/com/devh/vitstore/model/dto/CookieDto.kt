package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class CookieDto(
    @field:NotBlank
    val uuid: String,

    @field:NotBlank
    val token: String
) : Serializable
