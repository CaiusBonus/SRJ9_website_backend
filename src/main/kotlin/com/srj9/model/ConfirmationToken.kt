package com.srj9.model

import lombok.Data
import java.util.*
import javax.persistence.*

@Data
@Entity
@Table(name = "confirmation_token")
data class ConfirmationToken (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,

    @Column(name = "confirmation_token")
    var confirmationToken: String? = null,

    @Temporal(TemporalType.TIMESTAMP)
    var createdDate: Date? = null,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    var user: User ? = null
) {
    constructor(user: User) : this() {
        this.user = user
        createdDate = Date()
        confirmationToken = UUID.randomUUID().toString()
    }
}