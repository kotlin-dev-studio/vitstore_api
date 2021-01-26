package com.devh.vitstore.controller.api.v1

import com.devh.vitstore.config.JwtTokenUtil
import com.devh.vitstore.model.jwt.JwtRequest
import com.devh.vitstore.model.jwt.JwtResponse
import com.devh.vitstore.model.jwt.UserDto
import com.devh.vitstore.service.jwt.JwtUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class JwtAuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtUserDetailsService: JwtUserDetailsService
) {

    @PostMapping("/authenticate")
    @Throws(Exception::class)
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<JwtResponse> {
        val userDetails = jwtUserDetailsService.loadUserDetailsByEmail(authenticationRequest.email ?: "")
        authenticate(username = userDetails.username, password = authenticationRequest.password ?: "")
        val token = jwtTokenUtil.generateToken(userDetails)
        return ResponseEntity.ok(JwtResponse(token))
    }

    @PostMapping("/register")
    @Throws(Exception::class)
    fun registerUser(@RequestBody user: UserDto): ResponseEntity<Any> {
        val result = jwtUserDetailsService.registerUser(user)
        return ResponseEntity.ok(result)
    }

    @Throws(Exception::class)
    private fun authenticate(username: String, password: String) {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
