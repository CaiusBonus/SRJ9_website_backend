package com.srj9.model

import com.srj9.enums.Status
import lombok.*
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="LAUNDRY_RESERVATION")
data class LaundryReservation (

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    val id: Long,

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

    @ManyToOne
    @JoinColumn(name="user_id")
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name="washing_machine_id")
    var washing_machine: WashingMachine? = null
) {
}

