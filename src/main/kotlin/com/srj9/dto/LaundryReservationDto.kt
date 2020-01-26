package com.srj9.dto

import com.srj9.enums.Status
import java.util.*

data class LaundryReservationDto (
        val id: Long,
        val reservationNumber: String,
        val date: Date,
        val timeFrom: String,
        val timeUntil: String,
        val status: Status,
        val userId: Long,
        val washingMachineId: Long
)