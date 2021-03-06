package com.devh.vitstore.model.dto

import java.io.Serializable

data class UserDto(
    val email: String = "",
    val password: String? = null
) : Serializable
