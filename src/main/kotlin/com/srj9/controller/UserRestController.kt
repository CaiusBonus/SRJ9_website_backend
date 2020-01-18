package com.srj9.controller

import com.srj9.model.User
import com.srj9.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserRestController {


    @Autowired
    lateinit var userService: UserService

    @GetMapping("/user/{userId}")
    fun getSingleUser(@PathVariable userId: Long): User {
        return userService.getSingleUser(userId)
    }

}