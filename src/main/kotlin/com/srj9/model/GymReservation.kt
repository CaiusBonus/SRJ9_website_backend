package com.srj9.model

import com.srj9.enums.Status
import lombok.Data
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name="GYM_RESERVATION")
data class GymReservation (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0,

    @Column(name="reservation_number")
    var reservation_number: String? = null,

    @Column(name="date")
    var date: Date? = null,

    @Column(name="time_from")
    var time_from: Timestamp? = null,

    @Column(name="time_until")
    var time_until: Timestamp? = null,

    @Column(name="status")
    var status: Status? = null,

    @Column(name="gym_number")
    var gym_number: Int? = null
)
