package com.srj9.repository

import com.srj9.model.GymReservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GymReservationRepository: JpaRepository<GymReservation, Long> {

    @Query("SELECT r FROM GymReservation r WHERE r.date BETWEEN ?1 AND ?2")
    fun findGymReservationsBetweenFirstDayOfWeekAndLastDayOfWeek(firstDayOfWeek: Date, lastDayOfWeek: Date): List<GymReservation>
}
