package com.devh.vitstore.config

import com.devh.vitstore.service.jwt.JwtUserDetailsService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var jwtUserDetailsService: JwtUserDetailsService

    @Autowired
    private lateinit var jwtTokenUtil: JwtTokenUtil

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val requestTokenHeader = request.getHeader("Authorization")
        var username: String? = null
        var jwtToken: String = ""
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println("JWT Token has expired")
            } catch (e: UnsupportedJwtException) {
                logger.warn("JWT UnsupportedJwtException")
            } catch (e: MalformedJwtException) {
                logger.warn("JWT MalformedJwtException")
            } catch (e: SignatureException) {
                logger.warn("JWT SignatureException")
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String")
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = jwtUserDetailsService.loadUserByUsername(username)

            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                ).apply { details = WebAuthenticationDetailsSource().buildDetails((request)) }
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)
    }
}
