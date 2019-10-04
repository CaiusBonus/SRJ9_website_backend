package com.srj9.controller

import com.srj9.model.LaundryReservation
import com.srj9.service.LaundryReservationService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
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

    @ApiOperation(value = "Retreive all laundry reservations", notes = "Retrieves all laundry reservations", responseContainer = "List")
    @ApiResponse(code = 200, message = "Successfully retrieved list of laundry reservations")
    @GetMapping("/laundry_reservation")
    fun getAllLaundryReservations() : List<LaundryReservation> {
        return laundryReservationService.getAllLaundryReservations()
    }

    @ApiOperation(value = "Retrieve single laundry reservation", notes = "Retrieves single laundry reservation based on reservation ID")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieved single laundry reservation"),
            ApiResponse(code = 404, message = "Reservation is not found"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @GetMapping("/laundry_reservation/{reservationId}")
    fun getSingleLaundryReservation(@PathVariable reservationId: Long): LaundryReservation {
        return laundryReservationService.getSingleLaundryReservation(reservationId)
    }

    @ApiOperation(value = "Create a new laundry reservation", notes = "The newly created reservation object will be sent as the response")
    @ApiResponses(
            ApiResponse(code = 201, message = "Reservation successfully created"),
            ApiResponse(code = 400, message = "Input validation failed"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @PostMapping("/laundry_reservation")
    fun createLaundryReservation(@Valid @RequestBody laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationService.createLaundryReservation(laundryReservation)
    }

    @ApiOperation(value = "Retrieve all laundry reservations for current week", notes = "All laundry reservations for current week will be sent as the response", responseContainer = "List")
    @ApiResponses(
            ApiResponse(code = 200, message = "Successfully retrieved list of laundry reservations for current week"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @GetMapping("/laundry_reservation/current_week")
    fun getAllReservationsForCurrentWeek(): List<LaundryReservation> {
        return laundryReservationService.getAllReservationsForCurrentWeek()
    }

    @GetMapping("/laundry_reservation/available_hours")
    fun getAllAvailableHoursForSpecificDate(@PathVariable laundryDate: Date): String {
        return ""
    }

    @ApiOperation(value = "Update laundry reservation", notes = "Updates laundry reservation specified by reservationId with content in request body")
    @ApiResponses(
            ApiResponse(code = 200, message = "Reservation successfully updated"),
            ApiResponse(code = 404, message = "Reservation not found"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @PutMapping("/laundry_reservation/{reservationId}")
    fun updateExistingReservation(@RequestBody laundryReservation: LaundryReservation, @PathVariable reservationId: Long): ResponseEntity<LaundryReservation> {
        return laundryReservationService.updateExistingLaundryReservation(laundryReservation,reservationId)
    }

    @ApiOperation(value = "Delete laundry reservation", notes = "Delete laundry reservatoin specified by reservationId")
    @ApiResponses(
            ApiResponse(code = 200, message = "Reservation successfully deleted"),
            ApiResponse(code = 404, message = "Reservation not found"),
            ApiResponse(code = 500, message = "Internal Server Error")
    )
    @DeleteMapping("/laundry_reservation/{reservationId}")
    fun deleteExistingReservation(@PathVariable reservationId: Long): ResponseEntity<Void> {
        return laundryReservationService.deleteLaundryReservation(reservationId)
    }
}
