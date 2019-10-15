package com.srj9.repository

import com.srj9.model.Role
import com.srj9.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import com.srj9.model.RoleName
import com.sun.deploy.util.SearchPath.findOne
import java.util.*


@Repository
interface RoleRepository: JpaRepository<User, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}
