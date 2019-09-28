package com.srj9.controller

import com.srj9.service.GymReservationService
import com.srj9.model.GymReservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class GymReservationRestController {

    @Autowired
    lateinit var gymReservationService: GymReservationService

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/gym_reservation")
    fun getAllGymReservations(): List<GymReservation> {
        return gymReservationService.getAllGymReservations()
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/gym_reservation/{reservationId}")
    fun getSingleGymReservation(@PathVariable reservationId: Long): GymReservation {
        return gymReservationService.getSingleGymReservation(reservationId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PostMapping("/gym_reservation")
    fun createGymReservation(@Valid @RequestBody gymReservation: GymReservation) : GymReservation {
        return gymReservationService.createGymReservation(gymReservation)
    }
}
