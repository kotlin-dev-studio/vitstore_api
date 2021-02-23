package com.devh.vitstore.service.token

import com.devh.vitstore.model.dao.TokenDao
import com.devh.vitstore.model.dto.PublishingTokenRequest
import com.devh.vitstore.model.dto.TokenDto
import com.devh.vitstore.model.enum.TokenType
import com.devh.vitstore.repository.TokenRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenDetailService: TokenService {
    @Autowired
    private lateinit var tokenRepository: TokenRepository

    override fun publishToken(request: PublishingTokenRequest): TokenDto {
        val tokenNew = UUID.randomUUID().toString()
        val newToken = TokenDao().apply {
            romsid = request.romsid
            type = request.type
            expireDate = request.expireDate
        }
        when (request.type) {
            TokenType.REGISTRATION, TokenType.PWCHANGE -> {
                newToken.activeToken = tokenNew
            }
            TokenType.LOGIN -> {
                newToken.apply {
                    token = tokenNew
                    uuid = request.uuid
                }
            }
        }
        tokenRepository.save(newToken)
        return TokenDto(tokenNew)
    }
}
