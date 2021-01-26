package com.devh.vitstore.service.mail

import com.devh.vitstore.model.User
import com.devh.vitstore.service.UserService
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
    private val service: UserService? = null

    @Autowired
    private val messages: MessageSource? = null

    @Autowired
    private val mailSender: JavaMailSender? = null

    // API
    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user: User = event.user
        val token = UUID.randomUUID().toString() // TODO su dung cach genate token khac
        service?.saveActiveToken(user, token)
        val email: SimpleMailMessage = constructEmailMessage(event, user, token)
        mailSender?.send(email)
    }

    private fun constructEmailMessage(event: OnRegistrationCompleteEvent, user: User, token: String): SimpleMailMessage {
        val recipientAddress: String = user.email!!
        val subject = "Registration Confirmation"
        val confirmationUrl: String = event.appUrl + "/registrationConfirm?token=" + token
        val message = messages!!.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.locale)
        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("$message \r\n$confirmationUrl")
        return email
    }
}
