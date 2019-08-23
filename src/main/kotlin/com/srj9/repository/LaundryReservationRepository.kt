package com.srj9.repository

import com.srj9.model.LaundryReservation
import org.springframework.data.jpa.repository.JpaRepository

interface LaundryReservationRepository : JpaRepository<LaundryReservation, Long> {

}
