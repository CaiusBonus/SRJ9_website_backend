package com.srj9.model

import lombok.Data
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name = "TEMPORARY_RESERVATION")
data class TemporaryReservation (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "date")
    var date: Date? = null,

    @Column(name="time_from")
    var time_from: Timestamp? = null,

    @Column(name="time_until")
    var time_until: Timestamp? = null
)
