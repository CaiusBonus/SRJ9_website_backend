package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name = "NEW_TOPIC")
data class NewTopic (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name="name")
    var name: String? = null,

    @Column(name="description")
    var description: String? = null
)
