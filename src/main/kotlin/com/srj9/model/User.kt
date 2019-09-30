package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
@Table(name="APP_USER")
data class User (

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name="first_name")
    var first_name: String? = null,

    @Column(name="last_name")
    var last_name: String? = null,

    @Column(name="email")
    var email: String? = null,

    @Column(name="isic_id")
    var isic_id: String? = null,

    @Column(name="admin", columnDefinition="char(3)")
    var admin: Boolean? = false
)
