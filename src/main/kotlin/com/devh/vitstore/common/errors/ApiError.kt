package com.devh.vitstore.common.errors

import org.springframework.http.HttpStatus

data class ApiError(
    var status: HttpStatus,
    var errors: List<ApiErrorResponse>
) {
    constructor(status: HttpStatus, error: ApiErrorResponse) : this(status, arrayListOf<ApiErrorResponse>(error))
}
