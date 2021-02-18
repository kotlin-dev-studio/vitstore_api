package com.devh.vitstore.model.dto

import java.io.Serializable
import java.math.BigDecimal
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class JwtRequest(
    val email: String? = null,
    val password: String? = null
) : Serializable
