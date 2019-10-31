package com.srj9.security.jwt

import com.srj9.service.UserDetailsServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthTokenFilter: OncePerRequestFilter() {

    private val loggers = LoggerFactory.getLogger(JwtAuthTokenFilter::class.java)

    @Autowired
    lateinit var tokenProvider: JwtProvider

    @Autowired
    lateinit var userDetailsServiceImpl: UserDetailsServiceImpl

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {

            val jwt = getJwt(request)
            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
                val username = tokenProvider.getUserNameFromJwtToken(jwt)

                val userDetails = userDetailsServiceImpl.loadUserByUsername(username)
                val authentication = UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            loggers.error("Can NOT set user authentication -> Message: {}", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")

        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.replace("Bearer ", "")
        } else null

    }
}
