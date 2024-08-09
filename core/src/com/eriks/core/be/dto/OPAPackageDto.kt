package com.eriks.core.be.dto

import kotlinx.serialization.Serializable

@Serializable
class OPAPackageDto (
    var packName: String,
    var owner: String,
    var isOpen: Boolean,
    var isClaimed: Boolean,
    var description: String,
)