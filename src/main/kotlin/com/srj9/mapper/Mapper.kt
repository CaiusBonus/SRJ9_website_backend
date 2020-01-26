package com.srj9.mapper

import com.srj9.dto.GymReservationDto
import com.srj9.dto.LaundryReservationDto
import com.srj9.dto.RoomDto
import com.srj9.model.GymReservation
import com.srj9.model.LaundryReservation
import com.srj9.model.Room

fun Room.toDto() = RoomDto(
    id!!, users!!, block!!, isAvailable!!, capacity!!
)

fun GymReservation.toDto() = GymReservationDto(
    id!!, reservation_number!!, date!!, time_from!!, time_until!!, status!!, gym_number!!, user?.id!!
)

fun LaundryReservation.toDto() = LaundryReservationDto(
    id!!, reservation_number!!, date!!, time_from.toString(), time_until.toString(), status!!, user?.id!!, washing_machine?.id!!
)
