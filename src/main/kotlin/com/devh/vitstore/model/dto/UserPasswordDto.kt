package com.devh.vitstore.model.dto

import javax.validation.constraints.NotBlank

data class UserPasswordDto(
    @NotBlank
    val password: String,

    @NotBlank
    val activeToken: String
)
