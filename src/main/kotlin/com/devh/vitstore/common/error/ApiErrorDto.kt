package com.devh.vitstore.common.error

import java.io.Serializable

data class ApiErrorDto(
    var error_code: Int,
    var error_message: String
) : Serializable
