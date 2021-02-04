package com.devh.vitstore.model.dto

import com.devh.vitstore.common.error.ApiErrorDto
import java.io.Serializable

data class ErrorsDto(val errors: ArrayList<ApiErrorDto>) : Serializable
