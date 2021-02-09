package com.devh.vitstore.controller.api.v1.sample

import com.devh.vitstore.common.dto.ResultDataRes
import com.devh.vitstore.common.error.ApiErrorDto
import com.devh.vitstore.common.error.LocalizedException
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.Authorization
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import java.io.Serializable
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import javax.validation.constraints.NotBlank

// TODO: huorlk-0450 This controller will remove later

@RestController
@RequestMapping("/api/v1/sample")
class SampleController {
    @GetMapping(
        "/health",
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @ApiOperation(value = "Health check", authorizations = [Authorization(value = "Bearer")])
    fun health(): ResponseEntity<ResultDataRes<Health>> =
        ResponseEntity.ok(ResultDataRes.Success(Health("Alive at ${LocalDateTime.now()}")))

    class Health(
        var status: String
    ) : Serializable

    @GetMapping("/ex/testError")
    fun testError(request: HttpServletRequest): ResponseEntity<Any> {
        val errors = arrayListOf<Exception>(
            HttpMessageNotReadableException("Something went wrong with your request."),
            NoHandlerFoundException("HTTP", "/testError", HttpHeaders.EMPTY)
        )
        throw errors.random()
    }

    // https://www.programcreek.com/java-api-examples/?code=Vip-Augus%2Fspring-analysis-note%2Fspring-analysis-note-master%2Fspring-webmvc%2Fsrc%2Ftest%2Fjava%2Forg%2Fspringframework%2Fweb%2Fservlet%2Fmvc%2Fmethod%2Fannotation%2FResponseEntityExceptionHandlerTests.java
    @GetMapping("/ex/getExceptionMessage")
    fun getExceptionMessage(): ResponseEntity<Any> {
        var code = 0
        var message = ""
        try {
            val localizedException = LocalizedException("method_argument_not_valid", "NotBlank")
            message = localizedException.getLocalizedMessage()
            code = localizedException.getLocalizedCode()
        } catch (e: Exception) {
            println("Error retrieving properties $e")
        }
        return ResponseEntity.ok(ApiErrorDto(code, message))
    }

    // curl -X GET "http://localhost:8080/api/v1/sample/ex/httpRequestMethodNotSupportedException"
    @PostMapping("/ex/httpRequestMethodNotSupportedException")
    fun httpRequestMethodNotSupportedException(): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(123, "httpRequestMethodNotSupportedException"), HttpStatus.METHOD_NOT_ALLOWED)
    }

    // curl -X POST "http://localhost:8080/api/v1/sample/ex/httpMediaTypeNotSupportedException" -H "Content-Type: application/xml"
    @PostMapping("/ex/httpMediaTypeNotSupportedException", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun httpMediaTypeNotSupportedException(): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(
            ApiErrorDto(1003, "httpMediaTypeNotSupportedException"),
            HttpStatus.UNSUPPORTED_MEDIA_TYPE
        )
    }

    // curl -X POST "http://localhost:8080/api/v1/sample/ex/httpMediaTypeNotAcceptable" -H "Accept: application/json"
    @PostMapping("/ex/httpMediaTypeNotAcceptable", produces = [MediaType.APPLICATION_XML_VALUE])
    fun httpMediaTypeNotAcceptable(): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(1003, "httpMediaTypeNotAcceptable"), HttpStatus.BAD_REQUEST)
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/missingPathVariableException/sss/default'
    @GetMapping("/ex/missingPathVariableException/{id}/default")
    fun missingPathVariableException(
        @PathVariable(required = true) ids: String,
        request: HttpServletRequest
    ): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(123, "test"), HttpStatus.BAD_REQUEST)
    }

    // curl --location --request POST 'localhost:8080/api/v1/sample/ex/methodArgumentNotValidException' \
    //--header 'Content-Type: application/json' \
    //--data-raw '{
    //    "name": ""
    //}'
    @PostMapping("/ex/methodArgumentNotValidException")
    fun methodArgumentNotValidException(@Valid @RequestBody request: MethodArgumentNotValidExceptionDto): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(123, "test"), HttpStatus.BAD_REQUEST)
    }

    data class MethodArgumentNotValidExceptionDto(
        @field:NotBlank
        val name: String
    )

    // Send request without Authorization in header #ServletRequestBindingException
    // curl -X GET 'localhost:8080/api/v1/sample/ex/servletRequestBindingException'
    @GetMapping("/ex/servletRequestBindingException")
    fun servletRequestBindingException(@RequestHeader("Authorization") authorization: String): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(123, "test"), HttpStatus.BAD_REQUEST)
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/conversionNotSupportedException'
    @GetMapping("/ex/conversionNotSupportedException")
    fun conversionNotSupportedException() {
        val samples = listOf(String, Any())
        val ex: Exception = ConversionNotSupportedException(samples.random(), String::class.java, null)
        throw ex
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/typeMismatchException'
    @GetMapping("/ex/typeMismatchException")
    fun typeMismatchException() {
        throw TypeMismatchException(123, String::class.java)
    }

    // When send a request with id as String instead of long
    // curl -X GET 'localhost:8080/api/v1/sample/ex/methodArgumentTypeMismatchException/ss'
    @GetMapping("/ex/methodArgumentTypeMismatchException/{id}")
    fun methodArgumentTypeMismatchException(@PathVariable(required = false) id: Long): ResponseEntity<ApiErrorDto> {
        return ResponseEntity(ApiErrorDto(123, "test"), HttpStatus.BAD_REQUEST)
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/httpMessageNotWritableException'
    @GetMapping("/ex/httpMessageNotWritableException")
    fun httpMessageNotWritableException() {
        throw HttpMessageNotWritableException("msg string")
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/missingServletRequestPart'
    @GetMapping("/ex/missingServletRequestPart")
    fun missingServletRequestPart() {
        throw MissingServletRequestPartException("missing request part")
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/noHandlerFoundException'
    @GetMapping("/ex/noHandlerFoundException")
    fun noHandlerFoundException() {
        throw NoHandlerFoundException("GET", "/resource", HttpHeaders.EMPTY)
    }

    // curl -X GET 'localhost:8080/api/v1/sample/ex/asyncRequestTimeoutException'
    @GetMapping("/ex/asyncRequestTimeoutException")
    fun asyncRequestTimeoutException() {
        throw AsyncRequestTimeoutException()
    }
}
