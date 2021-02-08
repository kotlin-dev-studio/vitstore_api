package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class ChangeEmailRequest(
    @field:NotBlank
    val email: String,

    @field:NotBlank
    val token: String,

    @field:NotBlank
    val uuid: String
) : Serializable
