package com.devh.vitstore.service.jwt

import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.UserDao
import com.devh.vitstore.model.jwt.UserDto
import com.devh.vitstore.repository.UserRepository
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUserDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bcryptEncoder: PasswordEncoder

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserDao = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found with username: $username")
        return User(
            user.username, user.password,
            ArrayList()
        )
    }

    @Throws(NotFoundException::class)
    fun loadUserDetailsByEmail(email: String): UserDetails {
        val user = userRepository.findByEmailIgnoreCase(email)
            ?: throw NotFoundException("User not found with email: $email")
        return User(
            user.username, user.password,
            ArrayList()
        )
    }

    fun registerUser(user: UserDto): Any {
        if (userRepository.existsByEmail(user.email)) {
            return ResultRes.failure("Email existed")
        }
        val newUser = UserDao().apply {
            email = user.email
            password = bcryptEncoder.encode(user.password)
        }
        val userSaved = userRepository.save(newUser)
        userSaved.applyUsername()
        return userRepository.save(userSaved)
    }
}
