package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "WASHING_MACHINE")
class WashingMachine {

    companion object {
        const val SEQ = "TEMP_RESERVATION_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name = "number")
    var number: Number? = null

    @OneToMany
    var laundryReservation: LaundryReservation? = null
}
