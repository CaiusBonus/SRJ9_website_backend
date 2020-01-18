package com.srj9.controller

import com.srj9.model.Member
import com.srj9.service.MembersService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
@Api(value = "members", description = "Members in SRJ9")
class MembersRestController {

    @Autowired
    lateinit var membersService: MembersService

    @ApiOperation(value = "Retrieve all members", responseContainer = "List")
    @ApiResponse(code = 200, message = "Successfully retrieved list of members")
    @GetMapping("/members")
    fun getAllMembers(): List<Member> {
        return membersService.getAllMembers()
    }

    @ApiOperation(value = "Create new member", notes = "The newly created member object will be sent as the response")
    @ApiResponses(
            ApiResponse(code = 201, message = "Member successfully created"),
            ApiResponse(code = 400, message = "Input validation failed"),
            ApiResponse(code = 500, message = "Internat Server Error")
    )
    @PostMapping("/members")
    fun createMember(@Valid @RequestBody member: Member) : Member {
        return membersService.createMember(member)
    }

    @ApiOperation(value = "Update member", notes = "Updates member specified by memberId with content in request body")
    @ApiResponses(
            ApiResponse(code = 200, message = "Member successfully updated"),
            ApiResponse(code = 404, message = "Member not found"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @PutMapping("/members/{memberId}")
    fun updateExistingMember(@RequestBody member: Member, @PathVariable memberId: Long): Member {
        return membersService.updateExistingMember(member,memberId)
    }

    @ApiOperation(value = "Delete member", notes = "Delete member specified by memberId")
    @ApiResponses(
            ApiResponse(code = 200, message = "Member successfully deleted"),
            ApiResponse(code = 404, message = "Member not found"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @DeleteMapping("/members/{memberId}")
    fun deleteMember(@PathVariable memberId: Long): ResponseEntity<Void> {
        return membersService.deleteMember(memberId)
    }
}
