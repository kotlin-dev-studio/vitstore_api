package com.devh.vitstore.common.errors

import java.io.Serializable

class ApiErrorResponse(
    var error_code: Int,
    var error_message: String
) : Serializable
