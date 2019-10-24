package com.srj9.service

import com.srj9.model.Member
import com.srj9.repository.MembersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MembersService {

    @Autowired
    lateinit var membersRepository: MembersRepository

    fun getAllMembers(): List<Member> {
        return membersRepository.findAll()
    }
}
