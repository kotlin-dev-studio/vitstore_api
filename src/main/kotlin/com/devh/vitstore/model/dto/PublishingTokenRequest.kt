package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PublishingTokenRequest(
    @field:NotBlank
    val romsid: String,

    @field:NotBlank
    @field:Pattern(regexp = "^(REGISTRATION|PWCHANGE|LOGIN)$")
    val type: String,

    val uuid: String?,

    @field:NotBlank
    val token: String
) : Serializable
