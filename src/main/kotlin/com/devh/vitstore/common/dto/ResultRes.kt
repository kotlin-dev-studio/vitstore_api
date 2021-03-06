package com.devh.vitstore.common.dto

class ResultRes(
    val success: Boolean,
    val message: String
) {
    companion object {
        fun success(message: String = ""): ResultRes = ResultRes(true, message)

        fun failure(message: String = ""): ResultRes = ResultRes(false, message)
    }
}
