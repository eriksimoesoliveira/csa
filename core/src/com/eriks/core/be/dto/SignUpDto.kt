package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignUpDto(val userName: String, val password: String)