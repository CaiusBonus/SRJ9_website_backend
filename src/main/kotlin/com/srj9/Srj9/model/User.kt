package com.srj9.Srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
class User {

    companion object {
        const val SEQ = "USER_ID_SEQ"
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ)
    @SequenceGenerator(name = SEQ, sequenceName = SEQ, allocationSize = 1)
    @Column(name = "id")
    var id: Long? = null

    @Column(name="first_name")
    var first_name: String? = null

    @Column(name="last_name")
    var last_name: String? = null

    @Column(name="email")
    var email: String? = null

    @Column(name="isic_id")
    var isic_id: String? = null

    @Column(name="admin", columnDefinition="char(3)")
    var admin: Boolean? = false
}
