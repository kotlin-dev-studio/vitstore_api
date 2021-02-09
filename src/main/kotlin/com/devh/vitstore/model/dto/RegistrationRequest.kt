package com.devh.vitstore.model.dto

import java.io.Serializable
import java.util.*

data class RegistrationRequest(
    val email: String,
    val password: String,
    val sex: String?,
    val birthday: Date?,
    val postalCode: String?,
    val agreements: AgreementDto?
) : Serializable
