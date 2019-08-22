package com.srj9.Srj9.model

import java.util.*
import javax.persistence.*

class Event {

    companion object {
        const val SEQ = "EVENT_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name="start_date")
    var start_date: Date? = null

    @Column(name="end_date")
    var end_date: Date? = null

    @Column(name="name")
    var name: String? = null

    @Column(name="description")
    var description: String? = null
}
