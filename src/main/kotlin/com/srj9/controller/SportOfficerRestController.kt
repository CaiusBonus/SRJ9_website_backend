package com.srj9.controller

import com.srj9.model.SportOfficer
import com.srj9.service.SportOfficerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class SportOfficerRestController {

    @Autowired
    lateinit var sportOfficerService: SportOfficerService

    @GetMapping("/sport_officer")
    fun getAllSportOfficers(): List<SportOfficer> {
        return sportOfficerService.getAllSportOfficers()
    }

    @PostMapping("/sport_officer")
    fun createSportOfficer(sportOfficer: SportOfficer): SportOfficer {
        return sportOfficerService.createSportOfficer(sportOfficer)
    }

    @PutMapping("/sport_officer/{id}")
    fun updateSportOfficer(@RequestBody sportOfficer: SportOfficer, @PathVariable sportOfficerId: Long): SportOfficer {
        return sportOfficerService.updateSportOfficer(sportOfficer,sportOfficerId)
    }
}