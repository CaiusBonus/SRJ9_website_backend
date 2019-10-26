package com.srj9.security.message.response

import org.springframework.security.core.GrantedAuthority


class JwtResponse(var accessToken: String?, var username: String?, val authorities: Collection<GrantedAuthority>, var user_id: Long?) {
    var tokenType = "Bearer"
}
