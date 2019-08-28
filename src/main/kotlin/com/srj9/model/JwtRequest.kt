package com.srj9.model

import java.io.Serializable

class JwtRequest : Serializable{

    val serialVersionUID =  5926468583005150707L

    internal var username: String = ""
        get() = field
        set(value) {
            field = value
        }
    internal var password: String = ""
        get() = field
        set(value) {
            field = value
        }

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

    constructor()






}
