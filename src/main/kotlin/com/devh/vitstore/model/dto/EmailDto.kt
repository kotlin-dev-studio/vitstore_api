package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class EmailDto(
    @field:NotBlank
    val email: String,
) : Serializable
