package com.devh.vitstore.controller.api.v1

import com.devh.vitstore.common.dto.ResultRes
import com.devh.vitstore.config.JwtTokenUtil
import com.devh.vitstore.model.dto.JwtRequest
import com.devh.vitstore.model.dto.JwtResponse
import com.devh.vitstore.model.dto.UserDto
import com.devh.vitstore.model.enum.UserStatus
import com.devh.vitstore.service.jwt.JwtUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

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
    fun registerUser(@RequestBody user: UserDto, request: HttpServletRequest): ResponseEntity<Any> {
        val result = jwtUserDetailsService.registerUser(user, request.locale)
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

    @GetMapping("/confirmRegistration")
    @Throws(Exception::class)
    fun confirmRegistration(@RequestParam(name = "activeToken", required = true) activeToken: String): ResponseEntity<Any> {
        val user = jwtUserDetailsService.getUserByActiveToken(activeToken)
            ?: return ResponseEntity.ok(ResultRes.failure("InvalidToken or user activated"))

        if (user.status == UserStatus.APPROVED) return ResponseEntity.ok(ResultRes.failure("User was activated"))

        if (user.activeTokenExpiredAt!! <= LocalDateTime.now()) {
            return ResponseEntity.ok(ResultRes.failure("Expired!!!"))
        }
        jwtUserDetailsService.activeUser(user)
        return ResponseEntity.ok(ResultRes.success("Successfully"))
    }

    @PostMapping("/resendActiveToken")
    fun resendActiveToken(@RequestBody user: UserDto, request: HttpServletRequest): ResponseEntity<Any> {
        val userFound = jwtUserDetailsService.getUserByEmail(user.email)
        jwtUserDetailsService.resendActiveToken(userFound, request.locale)
        return ResponseEntity.ok(ResultRes.success("Successfully"))
    }
}
