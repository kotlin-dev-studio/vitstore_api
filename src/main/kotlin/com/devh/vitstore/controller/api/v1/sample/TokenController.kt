package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.model.dto.*
import io.swagger.annotations.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["TOKEN API"])
class TokenController {
    @PostMapping(
        "/v1/token/auth/romsid",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = AuthTokenResponse::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'romsid': '123'}"
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
                            value = "{'errors': [{'error_code': '609', 'error_message': 'TokenExpired'}," +
                                "{'error_code': '602', 'error_message': 'RecordInvalid'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 401,
                message = "Unauthorized",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '608', 'error_message': 'Unauthenticated'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 404,
                message = "Not Found",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '610', 'error_message': 'RecordNotFound'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 422,
                message = "Unprocessable Entity",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '604', 'error_message': 'type can't blank'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 2.2.1 - Authentication Token by ROMSID")
    @Throws(Exception::class)
    fun authTokenByRomsid(@Valid @RequestBody request: AuthTokenByRomsidRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(AuthTokenResponse("321"))
    }

    @PostMapping(
        "/v1/token/auth/uuid",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = AuthTokenResponse::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'romsid': '123'}"
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
                            value = "{'errors': [{'error_code': '609', 'error_message': 'TokenExpired'}," +
                                "{'error_code': '602', 'error_message': 'RecordInvalid'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 401,
                message = "Unauthorized",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '608', 'error_message': 'Unauthenticated'}]}"
                        )
                    ]
                )
            ),
            ApiResponse(
                code = 422,
                message = "Unprocessable Entity",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [{'error_code': '604', 'error_message': '\$name can't blank'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 2.2.2 - Authentication Token by UUID")
    @Throws(Exception::class)
    fun authTokenByUuid(@Valid @RequestBody request: AuthTokenByUuidRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(AuthTokenResponse("321"))
    }
}
