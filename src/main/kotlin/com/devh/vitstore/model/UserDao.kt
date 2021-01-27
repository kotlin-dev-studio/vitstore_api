package com.devh.vitstore.model

import com.devh.vitstore.common.model.BaseEntity
import com.devh.vitstore.model.user.Status
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserDao(
    @Column
    var email: String? = "",

    @Column
    var username: String? = "",

    @Column
    @JsonIgnore
    var password: String? = "",

    @Column
    @Enumerated
    var status: Status = Status.PENDING,

    @Column
    var phoneNumber: String? = null,

    @Column(name = "active_token")
    var activeToken: String? = null,

    @Column(name = "active_token_expired_at")
    var activeTokenExpiredAt: LocalDateTime? = LocalDateTime.now(),

) : BaseEntity<Long>() {
    companion object {
        private val prefixId = "ROMS"
    }

    fun applyUsername() {
        username = prefixId + id.toString()
    }
}
