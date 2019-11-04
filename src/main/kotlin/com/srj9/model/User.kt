package com.srj9.model

import lombok.Data

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

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

    @NotBlank
    @Size(max=50)
    @Column(name="email")
    var email: String? = null,

    @NotBlank
    @Size(min=3, max=50)
    @Column(name="username")
    var username: String? = null,

    @NotBlank
    @Size(min=8, max=100)
    @Column(name="password")
    var password: String? = null,


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "role_id")])
    var role: Set<Role> = HashSet()
) {
    constructor(first_name: String, last_name: String, username: String, email: String, password: String) : this() {
        this.first_name = first_name
        this.last_name = last_name
        this.username = username
        this.email = email
        this.password = password
    }
}

