package com.srj9.controller

import com.srj9.model.LaundryReservation
import com.srj9.service.LaundryReservationService
import org.springframework.beans.factory.annotation.Autowired
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
    @GetMapping("/laundry_reservation/{laundryId}")
    fun getSingleLaundryReservation(@PathVariable laundryId: Long): LaundryReservation {
        return laundryReservationService.getSingleLaundryReservation(laundryId)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @PostMapping("/laundry_reservation")
    fun createLaundryReservation(@Valid @RequestBody laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationService.createLaundryReservation(laundryReservation)
    }

    @CrossOrigin(origins = arrayOf("http://localhost:4200"))
    @GetMapping("/laundry_reservation/available_hours")
    fun getAllAvailableHoursForSpecificDate(@PathVariable laundryDate: Date): String {
        return ""
    }

}
