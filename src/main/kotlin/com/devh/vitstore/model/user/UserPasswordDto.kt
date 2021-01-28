package com.devh.vitstore.model.user

import javax.validation.constraints.NotBlank

data class UserPasswordDto(
    @NotBlank
    val password: String,

    @NotBlank
    val activeToken: String
)
