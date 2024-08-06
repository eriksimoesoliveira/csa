package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(val userId: String, val userName: String, val password: String)