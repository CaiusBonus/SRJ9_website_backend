package com.srj9.service

import com.srj9.model.LaundryReservation
import com.srj9.repository.LaundryReservationRepository
import com.srj9.util.LocalizedWeek
import com.srj9.util.toDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class LaundryReservationService {

    @Autowired
    lateinit var laundryReservationRepository: LaundryReservationRepository

    fun getAllLaundryReservations(): List<LaundryReservation> {
        return laundryReservationRepository.findAll()
    }

    fun getSingleLaundryReservation(laundryId: Long): LaundryReservation {
        return laundryReservationRepository.getOne(laundryId)
    }

    fun createLaundryReservation(laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationRepository.save(laundryReservation)
    }

    fun getAllReservationsForCurrentWeek(): List<LaundryReservation> {
        val localizedWeek = LocalizedWeek()
        return laundryReservationRepository.findLaundryReservationsByFirstDayOfWeekAndLastDayOfWeek(localizedWeek.getFirstDay().toDate(), localizedWeek.getLastDay().toDate())
    }

    fun updateExistingLaundryReservation(newLaundryReservation: LaundryReservation, reservationId: Long): ResponseEntity<LaundryReservation> {
        return laundryReservationRepository.findById(reservationId)
                .map {
                    existingLaundryReservation ->
                    val updatedReservation: LaundryReservation = existingLaundryReservation
                            .copy(reservation_number = newLaundryReservation.reservation_number,
                                    date = newLaundryReservation.date,
                                    time_from = newLaundryReservation.time_from,
                                    time_until = newLaundryReservation.time_until,
                                    status = newLaundryReservation.status)
                    ResponseEntity.ok().body(laundryReservationRepository.save(updatedReservation))
                }.orElse(ResponseEntity.notFound().build())
    }

    fun deleteLaundryReservation(reservationId: Long): ResponseEntity<Void> {
        return laundryReservationRepository.findById(reservationId)
                .map { reservation ->
                    laundryReservationRepository.delete(reservation)
                    ResponseEntity<Void>(HttpStatus.OK)
                }.orElse(ResponseEntity.notFound().build())
    }
}
