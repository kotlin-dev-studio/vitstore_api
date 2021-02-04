package com.devh.vitstore.model.dto

import java.io.Serializable

data class JwtResponse(val token: String, val uuid: String) : Serializable
