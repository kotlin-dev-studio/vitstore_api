package com.devh.vitstore.common.error

import io.swagger.annotations.ApiModelProperty
import java.io.Serializable



data class ApiErrorDto(
    var error_code: Int,
    var error_message: String
) : Serializable

//abstract class ApiErrorDto(
//    open var error_code: Int,
//    open var error_message: String
//)

//data class RecordInvalid(
//    @ApiModelProperty(example = "602")
//    override var error_code: Int,
//
//    @ApiModelProperty(example = "\$field is invalid")
//    override var error_message: String
//) : ApiErrorDto(602, "\$field is invalid")
//
//data class InvalidToken(
//    @ApiModelProperty(example = "603")
//    override var error_code: Int,
//
//    @ApiModelProperty(example = "Token is invalid")
//    override var error_message: String
//) : ApiErrorDto(603, "Token is invalid")
