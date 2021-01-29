package com.devh.vitstore.model.dto

import java.io.Serializable

data class JwtRequest(
    val email: String? = null,
    val password: String? = null
) : Serializable
