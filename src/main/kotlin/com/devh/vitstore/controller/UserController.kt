package com.devh.vitstore.controller

import com.devh.vitstore.model.User
import com.devh.vitstore.service.mail.OnRegistrationCompleteEvent
import com.devh.vitstore.service.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.web.bind.annotation.*
import java.io.UnsupportedEncodingException
import java.util.*
import javax.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment

@RestController
class UserController(
        private val userService: UserService,
        private val eventPublisher: ApplicationEventPublisher,
) {
    @Value("\${api.hostUrl}")
    private val hostUrl: String = ""

    @PostMapping("registration")
    fun registration(@RequestBody user: User, request: HttpServletRequest): String? {
        val email = user.email ?: return "Please enter email."
        if (userService.emailExist(email)) return "An account for that username/email already exists."
        val registered = userService.registerNewUserAccount(user)
//        val appUrl: String = request.contextPath
//        val appUrl: String = "http://localhost:8080"
        eventPublisher.publishEvent(OnRegistrationCompleteEvent(registered,
                request.locale, hostUrl))
        return "ok"
    }

    @GetMapping("/registrationConfirm")
    @Throws(UnsupportedEncodingException::class)
    fun confirmRegistration(@RequestParam("activeToken") activeToken: String): String {
        val user = userService.getUser(activeToken) ?: return "InvalidToken or user activated"
        if (user.activeTokenExpiredAt!! <= Calendar.getInstance().time) {
            return "Expired!!!"
        }
        userService.activeUser(user)
        return "ok"
    }
}
