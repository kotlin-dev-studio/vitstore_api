package com.devh.vitstore.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private val id: Long = 0,

        @Column
        var username: String? = "",

        @Column
        var email: String? = null,

        @Column
        var password: String? = null,

        @Column(name = "phone_number")
        var phoneNumber: String? = null,

        @Column
        var status: Int = 0,

        @Column(name = "active_token")
        var activeToken: String? = null,

        @Column(name = "active_token_expired_at")
        var activeTokenExpiredAt: Date? = Date(System.currentTimeMillis())
)
