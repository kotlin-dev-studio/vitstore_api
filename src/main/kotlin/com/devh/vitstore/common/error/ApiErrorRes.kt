package com.devh.vitstore.common.error

import org.springframework.http.HttpStatus

data class ApiErrorRes(
    var status: HttpStatus,
    var errors: List<ApiErrorDto>
) {
    constructor(status: HttpStatus, error: ApiErrorDto) : this(status, arrayListOf<ApiErrorDto>(error))
}
