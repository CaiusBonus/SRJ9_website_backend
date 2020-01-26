package com.srj9.model

import com.srj9.enums.Status
import lombok.Data
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name="GYM_RESERVATION", uniqueConstraints = [
    UniqueConstraint(columnNames = ["date",
                                    "time_from",
                                    "time_until",
                                    "gym_number"])
])
class GymReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    val id: Long? = 0

    @Column(name = "reservation_number")
    var reservation_number: String? = null

    @Column(name = "date")
    var date: Date? = null

    @Column(name = "time_from")
    var time_from: String? = null

    @Column(name = "time_until")
    var time_until: String? = null

    @Column(name = "status")
    var status: Status? = null

    @Column(name = "gym_number")
    var gym_number: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null
}
