package com.srj9.service

import com.srj9.model.Member
import com.srj9.repository.MembersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class MembersService {

    @Autowired
    lateinit var membersRepository: MembersRepository

    fun getAllMembers(): List<Member> {
        return membersRepository.findAll()
    }

    fun createMember(member: Member): Member {
        return membersRepository.save(member)
    }

    fun updateExistingMember(newMember: Member, memberId: Long): ResponseEntity<Member> {
        return membersRepository.findById(memberId)
                .map {
                    existingMember ->
                    val updatedMember: Member = existingMember
                            .copy(first_name =  newMember.first_name,
                                    last_name = newMember.last_name,
                                    position = newMember.position,
                                    email = newMember.email,
                                    description = newMember.description,
                                    photo_url = newMember.photo_url)
                    ResponseEntity.ok().body(membersRepository.save(updatedMember))
                }.orElse(ResponseEntity.notFound().build())
    }

    fun deleteMember(memberId: Long): ResponseEntity<Void> {
        return membersRepository.findById(memberId)
                .map { member ->
                    membersRepository.delete(member)
                    ResponseEntity<Void>(HttpStatus.OK)
                }.orElse(ResponseEntity.notFound().build())
    }
}
