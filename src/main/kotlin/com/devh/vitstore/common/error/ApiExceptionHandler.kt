package com.devh.vitstore.common.error

// TODO: huorlk-0450 TBD

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.beans.ConversionNotSupportedException
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.*
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.text.MessageFormat
import java.util.stream.Collectors

@RestControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        pageNotFoundLogger.warn(ex.message)

        ex.supportedHttpMethods?.let { headers.allow = it }

        val localizedException = LocalizedException("http_request_method_not_supported")
        val apiError = ApiErrorRes(
            HttpStatus.METHOD_NOT_ALLOWED,
            ApiErrorDto(localizedException.getLocalizedCode(), localizedException.getLocalizedMessage())
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        ex.supportedMediaTypes.let { headers.accept = it }

        val localizedException = LocalizedException("http_media_type_not_supported")
        val apiError = ApiErrorRes(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            ApiErrorDto(localizedException.getLocalizedCode(), localizedException.getLocalizedMessage())
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        ex.supportedMediaTypes.let { headers.accept = it }

        val localizedException = LocalizedException("http_media_type_not_supported")
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(localizedException.getLocalizedCode(), localizedException.getLocalizedMessage())
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("missing_path_variable")
        val apiError = ApiErrorRes(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    ex.variableName,
                    ex.parameter.nestedParameterType.simpleName
                )
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors: MutableList<ApiErrorDto> =
            ex.bindingResult.fieldErrors.stream().filter { it.code != null && it.defaultMessage?.isNotEmpty() == true }
                .map { fieldError ->
                    val localizedException = LocalizedException("method_argument_not_valid", fieldError.code.toString())
                    val message = MessageFormat.format(fieldError.defaultMessage.toString(), fieldError.field)
                    ApiErrorDto(localizedException.getLocalizedCode(), message)
                }.collect(Collectors.toList())

        val apiError = ApiErrorRes(HttpStatus.BAD_REQUEST, errors)

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("missing_parameter")
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(localizedException.getLocalizedMessage(), ex.parameterName)
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleServletRequestBindingException(
        ex: ServletRequestBindingException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val cause = ex.cause
        var localizedException = LocalizedException("servlet_request_binding")
        var code = localizedException.getLocalizedCode()
        var message = localizedException.getLocalizedMessage()

        when (cause) {
            is MissingMatrixVariableException -> {
                localizedException = LocalizedException("missing_matrix_variable")
                code = localizedException.getLocalizedCode()
                message = MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    cause.variableName,
                    cause.parameter.nestedParameterType.simpleName
                )
            }
            is MissingRequestCookieException -> {
                localizedException = LocalizedException("missing_request_cookie")
                code = localizedException.getLocalizedCode()
                message = MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    cause.cookieName,
                    cause.parameter.nestedParameterType.simpleName
                )
            }
            is MissingRequestHeaderException -> {
                localizedException = LocalizedException("missing_request_header")
                code = localizedException.getLocalizedCode()
                message = MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    cause.headerName,
                    cause.parameter.nestedParameterType.simpleName
                )
            }
        }

        val apiError = ApiErrorRes(HttpStatus.BAD_REQUEST, ApiErrorDto(code, message))
        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleConversionNotSupported(
        ex: ConversionNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("conversion_not_supported")
        val klassName = ex.value?.javaClass?.name ?: ex.javaClass.name
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    klassName,
                    ex.requiredType
                )
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("conversion_not_supported")
        val klassName = ex.value?.javaClass?.name ?: ex.javaClass.name
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(
                    localizedException.getLocalizedMessage(),
                    klassName,
                    ex.requiredType
                )
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val cause = ex.cause
        val localizedException = LocalizedException("http_message_not_readable")
        var apiError = ApiErrorRes(
            HttpStatus.UNPROCESSABLE_ENTITY,
            ApiErrorDto(localizedException.getLocalizedCode(), localizedException.getLocalizedMessage())
        )

        if (cause is MissingKotlinParameterException) {
            val violations = listOf(createMissingKotlinParameterViolation(cause))
            apiError = ApiErrorRes(HttpStatus.UNPROCESSABLE_ENTITY, violations)
        }

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("http_message_not_writable")
        val apiError = ApiErrorRes(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(localizedException.getLocalizedMessage(), ex.message).trim()
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleMissingServletRequestPart(
        ex: MissingServletRequestPartException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("missing_servlet_request_part")
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(localizedException.getLocalizedMessage(), ex.requestPartName).trim()
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("no_handler_found")
        val apiError = ApiErrorRes(
            HttpStatus.BAD_REQUEST,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(localizedException.getLocalizedMessage(), ex.httpMethod, ex.requestURL).trim()
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleAsyncRequestTimeoutException(
        ex: AsyncRequestTimeoutException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val localizedException = LocalizedException("async_request_timeout")
        val apiError = ApiErrorRes(
            HttpStatus.SERVICE_UNAVAILABLE,
            ApiErrorDto(
                localizedException.getLocalizedCode(),
                MessageFormat.format(localizedException.getLocalizedMessage())
            )
        )

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    private fun createMissingKotlinParameterViolation(cause: MissingKotlinParameterException): ApiErrorDto {
        val name = cause.path.fold("") { jsonPath, ref ->
            val suffix = when {
                ref.index > -1 -> "[${ref.index}]"
                else -> ".${ref.fieldName}"
            }
            (jsonPath + suffix).removePrefix(".")
        }

        val localizedException = LocalizedException("missing_parameter")
        return ApiErrorDto(
            localizedException.getLocalizedCode(),
            MessageFormat.format(localizedException.getLocalizedMessage(), name)
        )
    }
}
