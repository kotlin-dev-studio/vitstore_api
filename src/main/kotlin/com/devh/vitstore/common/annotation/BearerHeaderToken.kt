package com.devh.vitstore.common.annotation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams

@ApiImplicitParams(
    ApiImplicitParam(
        name = "Authorization",
        value = "Bearer Token",
        required = true,
        allowEmptyValue = false,
        paramType = "header",
        dataTypeClass = String::class,
        defaultValue = "Bearer token"
    )
)
annotation class BearerHeaderToken
