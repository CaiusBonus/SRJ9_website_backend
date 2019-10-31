package com.srj9.repository

import com.srj9.model.Role
import com.srj9.model.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(roleName: RoleName): Optional<Role>
}
