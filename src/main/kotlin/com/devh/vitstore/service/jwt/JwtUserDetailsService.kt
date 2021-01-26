package com.devh.vitstore.service.jwt

import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.UserDao
import com.devh.vitstore.model.jwt.UserDto
import com.devh.vitstore.model.user.Status
import com.devh.vitstore.repository.UserRepository
import com.devh.vitstore.service.mail.OnRegistrationCompleteEvent
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class JwtUserDetailsService : UserDetailsService {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var bcryptEncoder: PasswordEncoder

    @Autowired
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Value("\${api.hostUrl}")
    private val hostUrl: String = ""

    @Value("\${activeToken.expireTimeInDay}")
    private val expireTimeInDay: Long = 0

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

    fun registerUser(user: UserDto, locale: Locale): Any {
        if (userRepository.existsByEmail(user.email)) {
            return ResultRes.failure("Email existed")
        }
        val newUser = UserDao().apply {
            email = user.email
            password = bcryptEncoder.encode(user.password)
        }
        val userSaved = userRepository.save(newUser)
        userSaved.applyUsername()
        eventPublisher.publishEvent(
            OnRegistrationCompleteEvent(userSaved, locale, hostUrl)
        )
        return userRepository.save(userSaved)
    }

    fun saveActiveToken(user: UserDao, token: String?) {
        user.apply {
            activeToken = token
            activeTokenExpiredAt = LocalDateTime.now().plusDays(expireTimeInDay)
        }
        userRepository.save(user)
    }

    fun getUserByActiveToken(verificationToken: String): UserDao? {
        return userRepository.findByActiveToken(verificationToken)
    }

    fun activeUser(user: UserDao) {
        user.apply {
            status = Status.APPROVED
            activeToken = null
            activeTokenExpiredAt = null
        }
        userRepository.save(user)
    }
}
