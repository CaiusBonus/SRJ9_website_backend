package com.srj9.model

import com.srj9.enums.Status
import lombok.Data
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name="GYM_RESERVATION")
class GymReservation {

    companion object {
        const val SEQ = "GYM_RESERVATION_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name="reservation_number")
    var reservation_number: String? = null

    @Column(name="date")
    var date: Date? = null

    @Column(name="time_from")
    var time_from: Timestamp? = null

    @Column(name="time_until")
    var time_until: Timestamp? = null

    @Column(name="status")
    var status: Status? = null

    @Column(name="washing_machine_number")
    var gym_number: Int? = null
}
