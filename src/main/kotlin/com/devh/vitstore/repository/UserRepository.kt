package com.devh.vitstore.repository

import com.devh.vitstore.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long?> {
    fun findByActiveToken(activeToken: String): User?
    fun findByEmail(email: String): User?
}
