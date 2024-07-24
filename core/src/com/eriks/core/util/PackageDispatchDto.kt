package com.eriks.core.util

class PackageDispatchDto {

    lateinit var packList: List<PackageDispatchInner>

    class PackageDispatchInner {
        lateinit var id: String
        lateinit var players: List<String>
    }
}
