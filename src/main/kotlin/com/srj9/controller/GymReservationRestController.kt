package com.srj9.controller

import com.srj9.model.GymReservation
import com.srj9.repository.GymReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class GymReservationRestController {

    @Autowired
    lateinit var gymReservationRepository: GymReservationRepository

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/gym_reservation")
    fun getAllGymReservations(): List<GymReservation> {
        return gymReservationRepository.findAll()
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/gym_reservation/{reservationId}")
    fun getSingleGymReservation(@PathVariable reservationId: Long): GymReservation {
        return gymReservationRepository.getOne(reservationId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PostMapping("/gym_reservation")
    fun createGymReservation(@Valid @RequestBody gymReservation: GymReservation) : GymReservation {
        return gymReservationRepository.save(gymReservation)
    }
}
