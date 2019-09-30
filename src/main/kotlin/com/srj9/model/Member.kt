package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
data class Member (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = 0,

    @Column(name="first_name")
    var first_name: String? = null,

    @Column(name="last_name")
    var last_name: String? = null,

    @Column(name="position")
    var position: String? = null,

    @Column(name="email")
    var email: String? = null,

    @Column(name="description")
    var description: String? = null,

    @Column(name="photo_url")
    var photo_url: String? = null

)
