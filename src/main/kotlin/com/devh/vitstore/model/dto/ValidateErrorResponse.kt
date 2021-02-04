package com.devh.vitstore.model.dto

import java.io.Serializable

data class ValidateErrorResponse(val errors: ArrayList<ErrorValidate>) : Serializable

data class ErrorValidate(val error_code: Int, val message: String) : Serializable
