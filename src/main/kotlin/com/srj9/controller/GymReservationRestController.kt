package com.srj9.controller

import com.srj9.service.GymReservationService
import com.srj9.model.GymReservation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class GymReservationRestController {

    @Autowired
    lateinit var gymReservationService: GymReservationService

    @GetMapping("/gym_reservation")
    fun getAllGymReservations(): List<GymReservation> {
        return gymReservationService.getAllGymReservations()
    }

    @GetMapping("/gym_reservation/{reservationId}")
    fun getSingleGymReservation(@PathVariable reservationId: Long): GymReservation {
        return gymReservationService.getSingleGymReservation(reservationId)
    }

    @PostMapping("/gym_reservation")
    fun createGymReservation(@Valid @RequestBody gymReservation: GymReservation) : GymReservation {
        return gymReservationService.createGymReservation(gymReservation)
    }

    @GetMapping("/gym_reservation/current_week")
    fun getAllReservationsForCurrentWeek(): List<GymReservation> {
        return gymReservationService.getAllReservationsForCurrentWeek()
    }

    @PutMapping("/gym_reservation/{reservationId}")
    fun updateSingleGymReservation(@RequestBody gymReservation: GymReservation, @PathVariable reservationId: Long): ResponseEntity<GymReservation> {
        return gymReservationService.updateExistingGymReservation(gymReservation,reservationId)
    }

    @DeleteMapping("/gym_reservation/{reservationId}")
    fun deleteExistingGymReservation(@PathVariable reservationId: Long): ResponseEntity<Void> {
        return gymReservationService.deleteGymReservation(reservationId)
    }
}
