package com.srj9.repository

import com.srj9.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MembersRepository: JpaRepository<Member, Long> {
}
