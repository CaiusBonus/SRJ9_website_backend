package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "WASHING_MACHINE")
data class WashingMachine (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "number")
    var number: Number? = null

//    @OneToMany
//    var laundryReservation: LaundryReservation? = null
)
