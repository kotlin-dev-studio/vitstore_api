package com.devh.vitstore.model.dto

import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UpdateUserInfoRequest(
    @field:NotBlank
    val uuid: String,

    @field:NotBlank
    val token: String,

    @field:Pattern(regexp = "^(m|f|o)$")
    val sex: String?,

    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:MM:SSZ")
    val birthday: Date?,

    val postalCode: String?,
    val agreements: AgreementDto?
) : Serializable
