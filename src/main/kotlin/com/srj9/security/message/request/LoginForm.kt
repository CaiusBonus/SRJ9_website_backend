package com.srj9.security.message.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

class LoginForm {
    @NotBlank
    @Size(min = 3, max = 60)
    var username: String? = null

    @NotBlank
    @Size(min = 6, max = 40)
    var password: String? = null
}
