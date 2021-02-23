package com.devh.vitstore.service.token

import com.devh.vitstore.model.dto.PublishingTokenRequest
import com.devh.vitstore.model.dto.TokenDto

interface TokenService {
    fun publishToken(request: PublishingTokenRequest): TokenDto
}
