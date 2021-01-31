package com.devh.vitstore.service.jwt

import com.devh.vitstore.common.model.ResultRes
import com.devh.vitstore.model.jwt.UserDto
import com.devh.vitstore.model.user.Status
import com.devh.vitstore.model.user.UserDao
import com.devh.vitstore.repository.UserRepository
import com.devh.vitstore.service.mail.SendEmailEvent
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
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

    @Autowired
    private lateinit var messages: MessageSource

    @Value("\${api.hostUrl}")
    private val hostUrl: String = ""

    @Value("\${activeToken.expireTimeInDay}")
    private val expireTimeInDay: Long = 0

    @Value("\${mail.code.regSuccLink}")
    private val regSuccLink: String = ""

    @Value("\${mail.code.resSuccActiveToken}")
    private val resSuccActiveToken: String = ""

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
        sendEmailRegister(userSaved, locale)

        return userRepository.save(userSaved)
    }

    fun saveActiveToken(user: UserDao) {
        val activeTokenNew = UUID.randomUUID().toString() // TODO su dung cach genate token khac

        user.apply {
            activeToken = activeTokenNew
            activeTokenExpiredAt = LocalDateTime.now().plusDays(expireTimeInDay)
        }
        userRepository.save(user)
    }

    fun getUserByActiveToken(verificationToken: String): UserDao? {
        return userRepository.findByActiveToken(verificationToken)
    }

    fun getUserByEmail(email: String): UserDao {
        return userRepository.findByEmailIgnoreCase(email)
            ?: throw NotFoundException("User not found with email: $email")
    }

    fun activeUser(user: UserDao) {
        user.apply {
            status = Status.APPROVED
            activeToken = null
            activeTokenExpiredAt = null
        }
        userRepository.save(user)
    }

    fun resendActiveToken(user: UserDao, locale: Locale) {
        sendEmailReActive(user, locale)
    }

    private fun sendEmailRegister(user: UserDao, locale: Locale) {
        saveActiveToken(user)

        val recipientAddress = user.email
        val subject = "Registration Confirmation"
        val confirmationUrl = "$hostUrl/confirmRegistration?activeToken=${user.activeToken}"
        val message = messages.getMessage(regSuccLink, null, "You registered successfully. To confirm your registration, please click on the below link.", locale)
        eventPublisher.publishEvent(
            SendEmailEvent(user, recipientAddress, subject, confirmationUrl, message)
        )
    }

    private fun sendEmailReActive(user: UserDao, locale: Locale) {
        saveActiveToken(user)

        val recipientAddress = user.email
        val subject = "Please verify active token"
        val confirmationUrl = "ActiveToken: ${user.activeToken}"
        val message = messages.getMessage(resSuccActiveToken, null, "You enter the verification code on the unrecognized device.", locale)
        eventPublisher.publishEvent(
            SendEmailEvent(user, recipientAddress, subject, confirmationUrl, message)
        )
    }

    fun updatePassWord(activeToken: String, password: String): Any {
        val user = getUserByActiveToken(activeToken)
            ?: return ResultRes.failure("InvalidToken or user activated")
        if (user.activeTokenExpiredAt!! <= LocalDateTime.now()) {
            return ResultRes.failure("Expired!!!")
        }
        user.password = bcryptEncoder.encode(password)
        return userRepository.save(user)
    }

    fun updateEmail(username: String, email: String): UserDao {
        val user = userRepository.findByUsername(username)
            ?: throw NotFoundException("User not found with email: $email")
        user.email = email
        saveActiveToken(user)
        return user
    }
}
