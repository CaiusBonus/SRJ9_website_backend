package com.srj9.repository

import com.srj9.model.GymReservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface GymReservationRepository: JpaRepository<GymReservation, Long> {

    @Query("SELECT r FROM GymReservation r WHERE r.date BETWEEN ?1 AND ?2")
    fun findGymReservationsBetweenFirstDayAndLastDay(firstDay: Date, lastDay: Date): List<GymReservation>

//    @Query("SELECT r FROM GymReservation r WHERE r.user_id LIKE ?1")
    fun findGymReservationsByUserId(userId: Long): List<GymReservation>
}
