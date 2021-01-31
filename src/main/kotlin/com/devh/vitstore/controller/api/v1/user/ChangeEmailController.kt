package com.devh.vitstore.controller.api.v1.user

import com.devh.vitstore.model.jwt.UserDto
import com.devh.vitstore.service.jwt.JwtUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@CrossOrigin
class ChangeEmailController(
    private val jwtUserDetailsService: JwtUserDetailsService,
    private val authenticationManager: AuthenticationManager,
) {
    @PostMapping("/users/changeEmail")
    @Throws(Exception::class)
    fun changePassWord(@Valid @RequestBody user: UserDto, request: HttpServletRequest): ResponseEntity<Any> {
        val currentUser = SecurityContextHolder.getContext().authentication.principal as User
        val username = currentUser.username
        val result = jwtUserDetailsService.updateEmail(username, user.email)
        return ResponseEntity.ok(result)
    }
}
