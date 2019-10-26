package com.srj9.service

import com.fasterxml.jackson.annotation.JsonIgnore
import com.srj9.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

class UserPrinciple: UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user_authorities as MutableCollection<out GrantedAuthority>
    }

    override fun getUsername(): String {
        return user_username
    }

    override fun getPassword(): String {
        return user_password
    }


    override fun isEnabled(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    var id: Long = 0
    var name: String = ""
    var user_username: String = ""
    var email: String = ""

    @JsonIgnore
    var user_password: String = ""

    var user_authorities: Collection<GrantedAuthority>

    constructor(id: Long, name: String, username: String, email: String, password: String, authorities: Collection<GrantedAuthority>) {
        this.id = id
        this.name = name
        this.user_username = username
        this.email = email
        this.user_password = password
        this.user_authorities = authorities
    }

    companion object Principle {
        fun build(user: User): UserPrinciple {
            var authorities: List<GrantedAuthority> = user.role.stream().map { role ->
                SimpleGrantedAuthority(role.name?.name)
            }.collect(Collectors.toList())

            return UserPrinciple(
                    user.id!!, user.first_name!!, user.username!!, user.email!!, user.password!!, authorities
            )
        }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val user = o as UserPrinciple?
        return id == user!!.id
    }


}
