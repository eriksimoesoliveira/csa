package com.eriks.core.objects

import java.time.Instant
import java.util.*

class CardPackage(val id: String, val isOpen: Boolean, val origin: PackageOrigin, val date: Instant) {

    companion object {
        fun generateNew(origin: PackageOrigin): CardPackage = CardPackage(UUID.randomUUID().toString(), false, origin, Instant.now())
    }

}