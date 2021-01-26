package com.devh.vitstore.service.mail

import com.devh.vitstore.model.User
import org.springframework.context.ApplicationEvent
import java.util.*

class OnRegistrationCompleteEvent(
        val user: User, val locale: Locale, val appUrl: String,
) : ApplicationEvent(user)
