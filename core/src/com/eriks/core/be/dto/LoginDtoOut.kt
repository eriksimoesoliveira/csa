package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
class LoginDtoOut (
    val userId: String,
    val token: String
)