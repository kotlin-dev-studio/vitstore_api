package com.devh.vitstore.service.mail

import com.devh.vitstore.model.UserDao
import com.devh.vitstore.service.jwt.JwtUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegistrationListener : ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Autowired
    private lateinit var messages: MessageSource

    @Autowired
    private lateinit var mailSender: JavaMailSender

    // API
    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user = event.user
        val token = UUID.randomUUID().toString() // TODO su dung cach genate token khac
        jwtUserDetailsService.saveActiveToken(user, token)
        val email: SimpleMailMessage = constructEmailMessage(event, user, token)
        mailSender.send(email)
    }

    private fun constructEmailMessage(event: OnRegistrationCompleteEvent, user: UserDao, token: String): SimpleMailMessage {
        val recipientAddress = user.email
        val subject = "Registration Confirmation"
        val confirmationUrl = "${event.appUrl}/confirmRegistration?activeToken=$token"
        val message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.locale)
        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("$message \r\n$confirmationUrl")
        return email
    }
}
