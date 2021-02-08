package com.devh.vitstore.model.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank

data class UserPasswordDto(
    @NotBlank
    val password: String,

    @NotBlank
    val activeToken: String
) : Serializable
