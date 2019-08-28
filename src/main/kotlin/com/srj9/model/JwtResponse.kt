package com.srj9.model

import java.io.Serializable

class JwtResponse : Serializable {

    var serialVersionUID = -8091879091924046844L;
    internal var jwtToken: String

    constructor(jwtToken: String) {
        this.jwtToken = jwtToken
    }
}
