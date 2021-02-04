package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.model.dto.ErrorsDto
import com.devh.vitstore.model.dto.JwtRequest
import com.devh.vitstore.model.dto.JwtResponse
import com.devh.vitstore.model.sample.RegistrationRequestDto
import com.devh.vitstore.model.sample.RegistrationResDto
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    @PostMapping("/v1/user/register")
    @ApiResponses(
        value = [
            ApiResponse(
                code = 201,
                message = "Registered User",
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'active_token': 'example', 'type': 'REGISTRATION'}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 400,
                message = "Bad Request",
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '600', 'error_message': 'Email existed'}," +
                                "{'error_code': '606', 'error_message': 'Missing email or password'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 422,
                message = "Unprocessable Entity",
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '601', 'error_message': 'Incorrect email format'}," +
                                "{'error_code': '607', 'error_message': 'Invalid Birthday'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "Create a new user", notes = "Document for API 1.1")
    @Throws(Exception::class)
    fun registerUser(@RequestBody user: RegistrationRequestDto): ResponseEntity<Any> {
        val res = RegistrationResDto("754e9862-018e-4dce-8dd7-fca4e0ace9bf", "REGISTRATION")
        return ResponseEntity<Any>(res, HttpStatus.CREATED)
    }

    @PostMapping("/v1/user/authenticate")
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "Resource created",
                response = JwtResponse::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'token' : 'xxxxxx', 'uuid': 'xxxxxx'}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 400,
                message = "Bad Request",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [" +
                                "{'error_code': 605, 'error_message': 'User not active yet'}," +
                                "{'error_code': 602, 'error_message': 'Invalid email or password'}" +
                                "]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "Login user", notes = "Document for API 1.4")
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest) {
    }
}
