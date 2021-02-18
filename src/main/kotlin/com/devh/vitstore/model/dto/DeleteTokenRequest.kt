package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class DeleteTokenRequest(
    @field:NotBlank
    val type: String,

    @field:NotBlank
    val token: String,

    @field:NotBlank
    val uuid: String
) : Serializable
