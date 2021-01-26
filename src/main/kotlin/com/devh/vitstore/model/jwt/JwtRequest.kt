package com.devh.vitstore.model.jwt

import java.io.Serializable

class JwtRequest(
    val email: String? = null,
    val password: String? = null
) : Serializable
