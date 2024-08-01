package com.eriks.core.objects

import java.time.Instant

class CardPackage(val id: String, val isOpen: Boolean, val origin: PackageOrigin, val date: Instant, val type: Type) {

    enum class Type {
        REGULAR,
        RED,
        WHITE
    }

}