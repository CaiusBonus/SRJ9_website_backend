package com.srj9.model

import java.util.*
import javax.persistence.*

data class Event (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0,

    @Column(name="start_date")
    var start_date: Date? = null,

    @Column(name="end_date")
    var end_date: Date? = null,

    @Column(name="name")
    var name: String? = null,

    @Column(name="description")
    var description: String? = null
)
