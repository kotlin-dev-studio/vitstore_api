package com.devh.vitstore.model.dto

import java.io.Serializable

data class RegistrationResponse(
    val activeToken: String,
    val type: String
) : Serializable
