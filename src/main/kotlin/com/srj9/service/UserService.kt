package com.srj9.service

import com.srj9.model.User
import com.srj9.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getSingleUser(userId: Long): User {
        return userRepository.getOne(userId)
    }
}