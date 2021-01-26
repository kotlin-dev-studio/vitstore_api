package com.devh.vitstore.service

import com.devh.vitstore.model.User
import com.devh.vitstore.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@Transactional
class UserService {
    @Autowired
    private val repository: UserRepository? = null

    @Bean
    private fun bcryptEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun emailExist(email: String): Boolean {
        return repository?.findByEmail(email) != null
    }

    fun getUser(verificationToken: String): User? {
        return repository!!.findByActiveToken(verificationToken)
    }

    fun activeUser(user: User) {
        user.status = 1
        user.activeToken = null
        repository!!.save(user)
    }

    fun saveActiveToken(user: User?, token: String?) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow = calendar.time
        user?.activeToken = token
        user?.activeTokenExpiredAt = tomorrow
        repository!!.save(user!!)
    }

    fun registerNewUserAccount(user: User): User {
        val newUser = User()
        newUser.password = bcryptEncoder().encode(user.password)
        newUser.email = user.email

        return repository!!.save(newUser)
    }
}
