package com.devh.vitstore.model.dto

import java.io.Serializable
import java.util.*

data class UserInfoResponse(
    val romsid: String,
    val sex: String,
    val birthday: Date,
    val postalCode: String,
    val agreements: AgreementDto
) : Serializable
