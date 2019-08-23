package com.srj9.model

import lombok.Data
import javax.persistence.*

@Data
@Entity
class Member {

    companion object {
        const val SEQ = "MEMBER_ID_SEQ"
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

    @Column(name="poistion")
    var position: String? = null

    @Column(name="email")
    var email: String? = null

    @Column(name="description")
    var description: String? = null

    @Column(name="photo_url")
    var photo_url: String? = null

}
