package com.devh.vitstore.controller.api.v1.user

import com.devh.vitstore.common.dto.ResultDataRes
import com.devh.vitstore.model.dto.UserPasswordDto
import com.devh.vitstore.service.jwt.JwtUserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@CrossOrigin
class PasswordController(private val jwtUserDetailsService: JwtUserDetailsService) {
    @PostMapping("/changePassword")
    @Throws(Exception::class)
    fun changePassWord(@Valid @RequestBody user: UserPasswordDto): ResponseEntity<ResultDataRes<Any>> {
        val result = jwtUserDetailsService.updatePassWord(user.activeToken, user.password)
        return ResultDataRes.success(result)
    }
}
