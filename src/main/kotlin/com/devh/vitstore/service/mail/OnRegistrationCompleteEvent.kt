package com.devh.vitstore.service.mail

import com.devh.vitstore.model.UserDao
import org.springframework.context.ApplicationEvent
import java.util.*

class OnRegistrationCompleteEvent(
    val user: UserDao,
    val locale: Locale,
    val appUrl: String,
) : ApplicationEvent(user)
