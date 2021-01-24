package com.devh.vitstore.service.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class RegistrationListener : ApplicationListener<SendEmailEvent> {
    @Autowired
    private lateinit var mailSender: JavaMailSender

    @Value("\${mail.defaultFrom}")
    private val mailDefaultFrom: String = ""
    // API
    override fun onApplicationEvent(event: SendEmailEvent) {
        pushEmail(event)
    }

    private fun pushEmail(event: SendEmailEvent) {
        val email: SimpleMailMessage = constructEmailMessage(event)
        mailSender.send(email)
    }

    private fun constructEmailMessage(event: SendEmailEvent): SimpleMailMessage {
        val email = SimpleMailMessage()
        email.setFrom(mailDefaultFrom)
        email.setTo(event.recipientAddress)
        email.setSubject(event.subject)
        email.setText("${event.message} \r\n${event.confirmationUrl}")
        return email
    }
}
