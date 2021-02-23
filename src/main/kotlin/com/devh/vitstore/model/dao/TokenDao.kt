package com.devh.vitstore.model.dao

import com.devh.vitstore.common.dao.BaseEntity
import com.devh.vitstore.model.enum.TokenType
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.Table

@Value("\${app.token.expiredAtInHours}")
private val expiredAtInHours: Long = 0

@Entity
@Table(name = "tokens")
data class TokenDao(
    @Column
    var romsid: String = "",

    @Column
    var token: String? = null,

    @Column
    var uuid: String? = null,

    @Column
    @Enumerated
    var type: TokenType = TokenType.LOGIN,

    @Column(name = "active_token")
    var activeToken: String? = null,

    @Column(name = "expire_date")
    var expireDate: LocalDateTime? = LocalDateTime.now().plusHours(expiredAtInHours)
) : BaseEntity<Long>()
