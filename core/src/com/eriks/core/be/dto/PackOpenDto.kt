package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
data class PackOpenDto(
    val userId: String,
    val userName: String,
    val packId: String,
    val packType: String,
    val packOrigin: String
) 