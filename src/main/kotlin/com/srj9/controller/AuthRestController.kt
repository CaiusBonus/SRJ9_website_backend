package com.srj9.controller

import com.srj9.model.Role
import com.srj9.model.RoleName
import com.srj9.model.User
import com.srj9.repository.RoleRepository
import com.srj9.repository.UserRepository
import com.srj9.security.jwt.JwtProvider
import com.srj9.security.message.request.LoginForm
import com.srj9.security.message.request.SignUpForm
import com.srj9.security.message.response.JwtResponse
import com.srj9.security.message.response.ResponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/api/auth")
class AuthRestController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @PostMapping("/signin")
    fun authenticateUser(@Valid @RequestBody loginRequest: LoginForm): ResponseEntity<Any> {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        var jwt = jwtProvider.generateJwtToken(authentication)
        var userDetails : UserDetails = authentication.principal as UserDetails

        return ResponseEntity.ok(JwtResponse(jwt, userDetails.username, userDetails.authorities))
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody signUpRequest: SignUpForm): ResponseEntity<Any> {

        // Check if user is already registered
        if (userRepository.existsByUsername(signUpRequest.username!!)!!) {
            return ResponseEntity(ResponseMessage("Fail -> Username is already taken!"), HttpStatus.BAD_REQUEST)
        }

        if(userRepository.existsByEmail(signUpRequest.email!!)!!) {
            return ResponseEntity(ResponseMessage("Fail -> Email is already in use!"), HttpStatus.BAD_REQUEST)
        }

        // Create new user
        var user: User = User(signUpRequest.name!!, signUpRequest.username!!, signUpRequest.email!!, encoder.encode(signUpRequest.password))

        val strRoles: Set<String> = signUpRequest.role!!
        var roles: Set<Role> = HashSet()

        strRoles.forEach { role ->
            when (role) {
                "admin" -> {
                    val adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
                    roles = roles.plusElement(adminRole)
                }
                else -> {
                    val userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow { RuntimeException("Fail! -> Cause: User Role not find.") }
                    roles = roles.plusElement(userRole)
                }
            }
        }

        user.role = roles
        userRepository.save(user)

        return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
    }
}
