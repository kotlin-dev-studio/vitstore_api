package com.devh.vitstore.common.annotation

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization

@ApiImplicitParams(
    ApiImplicitParam(
        name = "Authorization",
        value = "Bearer TokenV",
        required = true,
        allowEmptyValue = true,
        paramType = "header",
        dataTypeClass = String::class,
        defaultValue = "Bearer tokenD"
    )
)
@ApiOperation(value = "Bearer tokenxxxx", authorizations = [Authorization("Authorization")])
annotation class BearerHeaderToken
