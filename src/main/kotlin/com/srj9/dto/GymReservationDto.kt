package com.srj9.dto

import com.srj9.enums.Status
import java.util.*

data class GymReservationDto (
     val id: Long,
     val reservationNumber: String,
     val date: Date,
     val timeFrom: String,
     val timeUntil: String,
     val status: Status,
     val gymNumber: Int,
     val userId: Long
)
