package com.devh.vitstore.model.dto

import com.devh.vitstore.model.enum.TokenType
import java.io.Serializable
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

data class PublishingTokenRequest(
    @field:NotBlank
    val romsid: String,

    val type: TokenType,

    val uuid: String?,

    val expireDate: LocalDateTime
) : Serializable
