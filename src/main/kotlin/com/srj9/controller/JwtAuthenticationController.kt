package com.srj9.controller

import com.srj9.config.JwtTokenUtil
import com.srj9.model.JwtRequest
import com.srj9.model.JwtResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception
import java.util.*

@RestController
@CrossOrigin
class JwtAuthenticationController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    @Qualifier("jwtUserDetailService")
    @Autowired
    lateinit var jwtInMemoryUserDetailsService: UserDetailsService

    @PostMapping(value = "/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: JwtRequest): ResponseEntity<Any> {
        authenticate(authenticationRequest.username, authenticationRequest.password)

        val userDetails : UserDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(authenticationRequest.username)

        val token : String = jwtTokenUtil.generateToken(userDetails)

        return ResponseEntity.ok(JwtResponse(token))
    }

    fun authenticate(username: String, password: String) {
        Objects.requireNonNull(username)
        Objects.requireNonNull(password)

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username,password))
        } catch (e: DisabledException) {
            throw Exception("USER_DISABLED", e)
        } catch (e: BadCredentialsException) {
            throw Exception("INVALID_CREDENTIALS", e)
        }
    }
}
