package com.srj9.controller

import com.srj9.model.Member
import com.srj9.service.MembersService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
