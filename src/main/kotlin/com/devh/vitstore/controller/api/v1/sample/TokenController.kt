package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.common.dto.ResultRes
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

    @DeleteMapping(
        "/v1/token",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = ResultRes::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'success': true, 'message': 'Delete successfully'}"
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
                            value = "{'errors': [" +
                                "{'error_code': 608, 'error_message': 'Unauthorized'}," +
                                "{'error_code': 609, 'error_message': 'TokenExpired'}" +
                                "]}"
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
                                "{'error_code': 602, 'error_message': 'RecordInvalid'}," +
                                "]}"
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
                            value = "{'errors': [{'error_code': 604, 'error_message': 'Type can't blank'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 2.3 - Delete token api")
    @Throws(Exception::class)
    fun deleteToken(@Valid @RequestBody request: DeleteTokenRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(ResultRes.success("Delete successfully"))
    }

    @PostMapping(
        "/v1/token",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = TokenDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'token' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito'}"
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
                            value = "{'errors': [{'error_code': 610, 'error_message': 'roms_id is not found'}]}"
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
    @ApiOperation(value = "API 2.1 - Publishing TOKEN api")
    @Throws(Exception::class)
    fun publishingToken(@Valid @RequestBody request: PublishingTokenRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito"))
    }
}
