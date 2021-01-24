package com.devh.vitstore.model.dao

import com.devh.vitstore.common.dao.BaseDao
import com.devh.vitstore.model.enum.UserStatus
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
    var status: UserStatus = UserStatus.PENDING,

    @Column
    var phoneNumber: String? = null,

    @Column(name = "active_token")
    var activeToken: String? = null,

    @Column(name = "active_token_expired_at")
    var activeTokenExpiredAt: LocalDateTime? = LocalDateTime.now(),

) : BaseDao<Long>() {
    companion object {
        private val prefixId = "ROMS"
    }

    fun applyUsername() {
        username = prefixId + id.toString()
    }
}
