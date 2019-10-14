package com.srj9.service

import com.srj9.model.GymReservation
import com.srj9.repository.GymReservationRepository
import com.srj9.util.LocalizedWeek
import com.srj9.util.toDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GymReservationService {

    @Autowired
    lateinit var gymReservationRepository: GymReservationRepository

    fun getAllGymReservations(): List<GymReservation> {
        return gymReservationRepository.findAll()
    }

    fun getSingleGymReservation(reservationId: Long): GymReservation {
        return gymReservationRepository.getOne(reservationId)
    }

    fun createGymReservation(gymReservation: GymReservation): GymReservation {
        return gymReservationRepository.save(gymReservation)
    }

    fun getAllReservationsForCurrentWeek(): List<GymReservation> {
        val localizedWeek = LocalizedWeek()
        return gymReservationRepository.findGymReservationsBetweenFirstDayAndLastDay(localizedWeek.getFirstDay().toDate(), localizedWeek.getLastDay().toDate())
    }

    fun getAllReservationsBetweenDates(from: LocalDate, to: LocalDate): List<GymReservation> {
        return gymReservationRepository.findGymReservationsBetweenFirstDayAndLastDay(from.toDate(), to.toDate())
    }

    fun updateExistingGymReservation(newGymReservation: GymReservation, reservationId: Long): ResponseEntity<GymReservation> {
        return gymReservationRepository.findById(reservationId)
                .map {
                    existingGymReservation ->
                    val updatedReservation: GymReservation = existingGymReservation
                            .copy(reservation_number =  newGymReservation.reservation_number,
                                    date = newGymReservation.date,
                                    time_from = newGymReservation.time_from,
                                    time_until = newGymReservation.time_until,
                                    status = newGymReservation.status,
                                    gym_number = newGymReservation.gym_number)
                    ResponseEntity.ok().body(gymReservationRepository.save(updatedReservation))
                }.orElse(ResponseEntity.notFound().build())
    }

    fun deleteGymReservation(reservationId: Long): ResponseEntity<Void> {
        return gymReservationRepository.findById(reservationId)
                .map { reservation ->
                    gymReservationRepository.delete(reservation)
                    ResponseEntity<Void>(HttpStatus.OK)
                }.orElse(ResponseEntity.notFound().build())
    }
}
