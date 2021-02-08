package com.devh.vitstore.common.error

// TODO: huorlk-0450 TBD

// import org.springframework.http.HttpHeaders
// import org.springframework.http.HttpStatus
// import org.springframework.http.ResponseEntity
// import org.springframework.http.converter.HttpMessageNotReadableException
// import org.springframework.web.bind.annotation.ControllerAdvice
// import org.springframework.web.context.request.WebRequest
// import org.springframework.web.servlet.NoHandlerFoundException
// import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
// import org.springframework.validation.FieldError
// import org.springframework.validation.ObjectError
//
// import java.util.HashMap
//
// import org.springframework.web.bind.MethodArgumentNotValidException
// import org.springframework.web.bind.annotation.ExceptionHandler
// import org.springframework.web.bind.annotation.ResponseStatus
// import java.util.function.Consumer

// @ControllerAdvice
// class ApiExceptionHandler : ResponseEntityExceptionHandler() {
//    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        val apiError = ApiErrorRes(status, ApiErrorDto(123, ex.localizedMessage))
//        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
//    }
//
//    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
//        val apiError = ApiErrorRes(HttpStatus.NOT_FOUND, arrayListOf(ApiErrorDto(456, ex.localizedMessage), ApiErrorDto(789, "No handler found for ")))
//        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
//    }
// }
