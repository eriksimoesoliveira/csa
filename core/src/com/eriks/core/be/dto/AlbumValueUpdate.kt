package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumValueUpdate(val userId: String, val userName: String, val value: Double)