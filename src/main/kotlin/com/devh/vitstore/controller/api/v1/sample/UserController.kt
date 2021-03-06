package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.common.dto.ResultRes
import com.devh.vitstore.model.dto.*
import io.swagger.annotations.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["ID API"])
class UserController {
    @PostMapping(
        "/v1/user/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 201,
                message = "Registered User",
                response = RegistrationResponse::class,
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
                response = ErrorsDto::class,
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
                response = ErrorsDto::class,
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
    @ApiOperation(value = "API 1.1 - Create a new user")
    @Throws(Exception::class)
    fun registerUser(@RequestBody user: RegistrationRequest): ResponseEntity<Any> {
        val res = RegistrationResponse("754e9862-018e-4dce-8dd7-fca4e0ace9bf", "REGISTRATION")
        return ResponseEntity<Any>(res, HttpStatus.CREATED)
    }

    @GetMapping(
        "/v1/user/confirmRegistration",
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
                            value = "{'success': true, 'message': 'Successfully'}"
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
                            value = "{'errors': [{'error_code': '603', 'error_message': 'Invalid Token'}," +
                                "{'error_code': '610', 'error_message': 'User Activated'}]}"
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
                            value = "{'errors': [{'error_code': '609', 'error_message': 'Token Expired'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.2 - Active account")
    @Throws(Exception::class)
    fun confirmRegistration(
        @RequestParam(name = "active_token", required = true) activeToken: String,
        @RequestParam(name = "type", required = true) type: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(ResultRes.success("Successfully"))
    }

    @PostMapping(
        "/v1/user/resendActiveToken",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = ActiveTokenDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'active_token' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito'}"
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
                                "{'error_code': 602, 'error_message': 'Invalid email or password'}," +
                                "{'error_code': 605, 'error_message': 'User not active yet'}," +
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
    @ApiOperation(value = "API 1.3 - Resend active token")
    @Throws(Exception::class)
    fun resendActiveToken(@Valid @RequestBody request: ResendActiveTokenRequest): ResponseEntity<Any> {
        val activeToken =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito"
        return ResponseEntity.ok(ActiveTokenDto(activeToken))
    }

    @PostMapping(
        "/v1/authenticate",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = CookieDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'token' : 'YuaJxCZ13LjkiuyqqoiKhus7ILoingTalj.1duqiuypOutBgAqiYthgJHGsa.wity13KJhagyowuak4nmgZzxvty', 'uuid': '123e4567-e89b-42d3-a456-556642440010'}"
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
    @ApiOperation(value = "API 1.4 - Login user")
    @Throws(Exception::class)
    fun createAuthenticationToken(@Valid @RequestBody authenticationRequest: JwtRequest): ResponseEntity<Any> {
        val response = CookieDto(
            "123e4567-e89b-42d3-a456-556642440010",
            "YuaJxCZ13LjkiuyqqoiKhus7ILoingTalj.1duqiuypOutBgAqiYthgJHGsa.wity13KJhagyowuak4nmgZzxvty"
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping(
        "/v1/refreshToken",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = CookieDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'token' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ'," +
                                " 'uuid': '123e4567-e89b-42d3-a456-556642440000'}"
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
                                "{'error_code': 608, 'error_message': 'Unauthenticated'}," +
                                "{'error_code': 609, 'error_message': 'TokenExpired'}" +
                                "]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.5 - Refresh token")
    @Throws(Exception::class)
    fun refreshToken(@Valid @RequestBody request: CookieDto): ResponseEntity<Any> {
        val response = CookieDto(
            "123e4567-e89b-42d3-a456-556642440010",
            "YuaJxCZ13LjkiuyqqoiKhus7ILoingTalj.1duqiuypOutBgAqiYthgJHGsa.wity13KJhagyowuak4nmgZzxvty"
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping(
        "/v1/user/update",
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
                            value = "{'success': true, 'message': 'Update info successfully'}"
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
                            value = "{'errors': [{'error_code': '608', 'error_message': 'Unauthenticated'}," +
                                "{'error_code': '609', 'error_message': 'TokenExpired'}]}"
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
                            value = "{'errors': [{'error_code': '607', 'error_message': 'Invalid birthday'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.6 - Update user information")
    @Throws(Exception::class)
    fun updateUserInfo(@Valid @RequestBody request: UpdateUserInfoRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(ResultRes.success("Update info successfully"))
    }

    @PostMapping(
        "/v1/user/changeEmail",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = ActiveTokenDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'active_token' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito'}"
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
                                "{'error_code': 608, 'error_message': 'Unauthenticated'}," +
                                "{'error_code': 609, 'error_message': 'TokenExpired'}" +
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
                            value = "{'errors': [" +
                                "{'error_code': 607, 'error_message': 'Email is invalid'}," +
                                "{'error_code': 604, 'error_message': 'Email can't blank'}" +
                                "]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.7 - Change user's email")
    @Throws(Exception::class)
    fun changeEmail(@Valid @RequestBody request: ChangeEmailRequest): ResponseEntity<Any> {
        val activeToken =
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJST01TMSIsImlhdCI6MTYxMjExMDQwNiwiZXhwIjoxNjEyMTI4NDA2fQ.jNjxsFbiJFDsd9lvhlS1Y2Q-ld1zVzcsP3v3U6v3Ito"
        return ResponseEntity.ok(ActiveTokenDto(activeToken))
    }

    @PostMapping(
        "/v1/user/changePassword",
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
                            value = "{'success': true, 'message': 'Update password successfully' }"
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
                            value = "{'errors': [" +
                                "{'error_code': 604, 'error_message': 'Please enter password'}," +
                                "]}"
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
                message = "Unauthorized",
                response = ErrorsDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'errors': [" +
                                "{'error_code': 602, 'error_message': 'Invalid'}," +
                                "]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.8 - Change user's password")
    @Throws(Exception::class)
    fun changePassword(@Valid @RequestBody request: ChangePasswordRequest): ResponseEntity<ResultRes> {
        return ResponseEntity.ok(ResultRes.success("Update password successfully"))
    }

    @GetMapping(
        "/v1/user/email",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiResponses(
        value = [
            ApiResponse(
                code = 200,
                message = "OK",
                response = EmailDto::class,
                examples = Example(
                    value = [
                        ExampleProperty(
                            mediaType = "application/json",
                            value = "{'email' : 'example@example.com'}"
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
                                "{'error_code': 608, 'error_message': 'Unauthenticated'}," +
                                "{'error_code': 609, 'error_message': 'TokenExpired'}" +
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
                            value = "{'errors': [{'error_code': '604', 'error_message': '\$name can't blank'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.10 - Get user's email")
    @Throws(Exception::class)
    fun getEmail(
        @RequestParam(name = "uuid", required = true) uuid: String,
        @RequestParam(name = "token", required = true) token: String
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(EmailDto("example@example.com"))
    }

    @DeleteMapping(
        "/v1/user",
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
                            value = "{'success': true, 'message': 'Successfully'}"
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
                                "{'error_code': 608, 'error_message': 'Unauthenticated'}," +
                                "{'error_code': 609, 'error_message': 'TokenExpired'}" +
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
                            value = "{'errors': [{'error_code': '604', 'error_message': '\$name can't blank'}]}"
                        )
                    ]
                )
            )
        ]
    )
    @ApiOperation(value = "API 1.11 - Deactivate user")
    @Throws(Exception::class)
    fun deActivate(@Valid @RequestBody request: CookieDto): ResponseEntity<Any> {
        return ResponseEntity.ok(ResultRes.success("Successfully"))
    }
}
