package com.srj9.service

import com.srj9.model.GymReservation
import com.srj9.repository.GymReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    fun createGymReservation(gymReservation: GymReservation) : GymReservation {
        return gymReservationRepository.save(gymReservation)
    }
}