package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.common.error.ApiErrorDto
import com.devh.vitstore.model.dto.JwtRequest
import com.devh.vitstore.model.dto.JwtResponse
import com.devh.vitstore.model.dto.ValidateErrorResponse
import io.swagger.annotations.*
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {
    @RequestMapping(
            path = ["v1/user/authenticate"],
            method = [RequestMethod.POST],
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(value = "some opeation")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Resource created", response = JwtResponse::class,
            examples = Example(value = [ExampleProperty(value = "{'snapshot'：{'type': 'AAA'}}")])
        ),
        ApiResponse(
            code = 400, message = "Bad Request", response = ApiErrorDto::class,
            examples = Example(value = [ExampleProperty(value = "{'code': '602', 'message': 'Record Invalid.'}", mediaType = "application/json" )]))
//        ApiResponse(
//            code = 400, message = "Invalid Token", response = InvalidToken::class
//        ),
    ])
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest) {
    }
}
