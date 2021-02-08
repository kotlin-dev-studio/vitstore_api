package com.devh.vitstore.model.sample

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.*

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class RegistrationRequestDto(
    val email: String,
    val password: String,
    val sex: String?,
    val birthday: Date?,
    val postalCode: String?,
    val agreements: AgreementDto?
)
