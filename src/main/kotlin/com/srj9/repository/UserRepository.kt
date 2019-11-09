package com.srj9.repository

import com.srj9.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean?
    fun existsByEmail(email: String): Boolean?
    fun findByEmail(email: String): User
}
