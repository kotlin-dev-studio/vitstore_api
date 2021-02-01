package com.devh.vitstore.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.security.Key
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtil : Serializable {
    @Value("\${jwt.secret}")
    private val secret: String = ""

    @Value("\${jwt.expireTimeInHour}")
    private val expireTimeInHour: Long = 0L

    // retrieve username from jwt token
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    // retrieve expiration date from jwt token
    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    // for retrieveing any information from token we will need the secret key
    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parserBuilder().setSigningKey(getHmacShaSecretBase64()).build().parseClaimsJws(token).body
    }

    // check if the token has expired
    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun ignoreTokenExpiration(token: String): Boolean {
        // here you specify tokens, for that the expiration is ignored
        return false
    }

    // generate token for user
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any?> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    // while creating the token -
    // 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    // 2. Sign the JWT using the HS256 algorithm and secret key.
    // 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private fun doGenerateToken(claims: Map<String, Any?>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + timeInMilliSeconds()))
            .signWith(getHmacShaSecretBase64(), SignatureAlgorithm.HS256).compact()
    }

    fun canTokenBeRefreshed(token: String): Boolean {
        return !isTokenExpired(token) || ignoreTokenExpiration(token)
    }

    // validate token
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    // Calculate JWT validity
    fun timeInMilliSeconds(): Long = expireTimeInHour * 60 * 60 * 1000

    // Decode secret key
    fun getHmacShaSecretBase64(): Key {
        val keyBytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}
