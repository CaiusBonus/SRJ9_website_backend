package com.srj9.model

import lombok.Data
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name = "TEMPORARY_RESERVATION")
class TemporaryReservation {

    companion object {
        const val SEQ = "TEMP_RESERVATION_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "date")
    var date: Date? = null

    @Column(name="time_from")
    var time_from: Timestamp? = null

    @Column(name="time_until")
    var time_until: Timestamp? = null
}
