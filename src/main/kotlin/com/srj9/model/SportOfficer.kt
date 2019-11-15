package com.srj9.model

import lombok.*
import javax.persistence.*


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name="Sport_Officer")
data class SportOfficer (

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    val id: Long? = 0,

    @Column(name="email")
    var email: String? = null
)