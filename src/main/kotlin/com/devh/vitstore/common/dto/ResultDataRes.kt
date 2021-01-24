package com.devh.vitstore.common.dto

import org.springframework.http.ResponseEntity

class ResultDataRes<out T>(
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> success(data: T, message: String = ""): ResponseEntity<ResultDataRes<T>> = ResponseEntity.ok(ResultDataRes(data, message))

        // TODO: huorlk-0450 To be define
        // fun failure(exception: Exception, message: String = "")
    }
}
