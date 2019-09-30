package com.srj9.controller

import com.srj9.model.LaundryReservation
import com.srj9.service.LaundryReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class LaundryReservationRestController {

    @Autowired
    lateinit var laundryReservationService: LaundryReservationService

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation")
    fun getAllLaundryReservations() : List<LaundryReservation> {
        return laundryReservationService.getAllLaundryReservations()
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/{reservationId}")
    fun getSingleLaundryReservation(@PathVariable reservationId: Long): LaundryReservation {
        return laundryReservationService.getSingleLaundryReservation(reservationId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PostMapping("/laundry_reservation")
    fun createLaundryReservation(@Valid @RequestBody laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationService.createLaundryReservation(laundryReservation)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/current_week")
    fun getAllReservationsForCurrentWeek(): List<LaundryReservation> {
        return laundryReservationService.getAllReservationsForCurrentWeek()
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/available_hours")
    fun getAllAvailableHoursForSpecificDate(@PathVariable laundryDate: Date): String {
        return ""
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PutMapping("/laundry_reservation/{reservationId}")
    fun updateExistingReservation(@RequestBody laundryReservation: LaundryReservation, @PathVariable reservationId: Long): ResponseEntity<LaundryReservation> {
        return laundryReservationService.updateExistingLaundryReservation(laundryReservation,reservationId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @DeleteMapping("/laundry_reservation/{reservationId}")
    fun deleteExistingReservation(@PathVariable reservationId: Long): ResponseEntity<Void> {
        return laundryReservationService.deleteLaundryReservation(reservationId)
    }

}
