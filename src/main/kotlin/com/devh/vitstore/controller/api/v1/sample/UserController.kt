package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.model.dto.ErrorsDto
import com.devh.vitstore.model.dto.JwtRequest
import com.devh.vitstore.model.dto.JwtResponse
import io.swagger.annotations.*
import org.springframework.web.bind.annotation.*

@RestController
class UserController {
    @PostMapping("v1/user/authenticate")
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
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest) {
    }
}
