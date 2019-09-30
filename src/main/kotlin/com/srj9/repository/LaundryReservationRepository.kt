package com.srj9.repository

import com.srj9.model.LaundryReservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface LaundryReservationRepository : JpaRepository<LaundryReservation, Long> {

//    @Query("SELECT r FROM LAUNDRY_RESERVATION r WHERE r.date BETWEEN ?1 AND ?2")
//    fun findLaundryReservationsByFirstDayOfWeekAndLastDayOfWeek(firstDayOfWeek: Date, lastDayOfWeek: Date): List<LaundryReservation>
}
