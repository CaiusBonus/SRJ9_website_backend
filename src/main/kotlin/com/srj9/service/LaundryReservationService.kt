package com.srj9.service

import com.srj9.model.LaundryReservation
import com.srj9.repository.LaundryReservationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LaundryReservationService {

    @Autowired
    lateinit var laundryReservationRepository: LaundryReservationRepository

    fun getAllLaundryReservations() : List<LaundryReservation> {
        return laundryReservationRepository.findAll()
    }

    fun getSingleLaundryReservation(laundryId: Long): LaundryReservation {
        return laundryReservationRepository.getOne(laundryId)
    }

    fun createLaundryReservation(laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationRepository.save(laundryReservation)
    }
}