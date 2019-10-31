package com.srj9.security.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletException
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component


@Component
class JwtAuthEntryPoint: AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(JwtAuthEntryPoint::class.java)

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, exception: AuthenticationException?) {
        logger.error("Unauthorized error. Message - {}", exception?.localizedMessage)
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
    }
}
