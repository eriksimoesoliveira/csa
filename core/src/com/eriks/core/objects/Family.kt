package com.eriks.core.objects

enum class Family(val fromId: Int,
                  val toId: Int,
                  val friendlyName: String,
                  val listName: String,
                  val iconName: String,
                  val bgName: String) {
    KILOWATT_CASE(1, 20, "Kilowatt Case", "Kilowatt", "collection/kilowat-logo.png", "collection/KILO-BACKGROUND.png"),
    REVOLUTION_CASE(21, 40, "Revolution Case", "Revolution", "collection/revolution-logo.png", "collection/REVOLUTION-BACKGROUND.png"),
    RECOIL_CASE(41, 60, "Recoil Case", "Recoil", "collection/recoil-logo.png", "collection/RECOIL-BACKGROUND.png"),
    NIGHTMARE_CASE(61, 80, "Dreams & Nightmare Case", "D&N", "collection/dream-logo.png", "collection/DN-BACKGROUND.png"),
    AGENTS_1(81, 100, "Agents Collection 1", "Agents 1", "collection/agents1-logo.png", "collection/AGENTS1-BACKGROUND.png")
}