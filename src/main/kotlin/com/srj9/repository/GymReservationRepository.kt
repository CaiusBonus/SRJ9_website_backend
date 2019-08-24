package com.srj9.repository

import com.srj9.model.GymReservation
import org.springframework.data.jpa.repository.JpaRepository

interface GymReservationRepository: JpaRepository<GymReservation, Long> {
}
