package com.devh.vitstore.extension

import org.springframework.security.core.userdetails.User

fun User.checkValid(): Boolean {
    return this.username == "abc"
}
