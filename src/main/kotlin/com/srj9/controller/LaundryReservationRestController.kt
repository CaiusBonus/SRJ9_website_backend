package com.srj9.controller

import com.srj9.model.LaundryReservation
import com.srj9.repository.LaundryReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class LaundryReservationRestController {

    @Autowired
    lateinit var laundryReservationRepository: LaundryReservationRepository

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation")
    fun getAllLaundryReservations() : List<LaundryReservation> {
        return laundryReservationRepository.findAll()
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/{laundryId}")
    fun getSingleLaundryReservation(@PathVariable laundryId: Long): LaundryReservation {
        return laundryReservationRepository.getOne(laundryId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PostMapping("/laundry_reservation")
    fun createLaundryReservation(@Valid @RequestBody laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationRepository.save(laundryReservation)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/available_hours")
    fun getAllAvailableHoursForSpecificDate(@PathVariable laundryDate: Date): String {
        return ""
    }

}
