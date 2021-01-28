package com.devh.vitstore.service.mail

import com.devh.vitstore.model.user.UserDao
import org.springframework.context.ApplicationEvent

class SendEmailEvent(
    user: UserDao,
    val recipientAddress: String? = null,
    val subject: String = "",
    val confirmationUrl: String? = null,
    val message: String? = null
) : ApplicationEvent(user)
