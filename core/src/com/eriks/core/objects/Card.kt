package com.eriks.core.objects

class Card(
    val id: String,
    val bluePrint: CardBluePrint,
    val weaponFloat: Condition,
    val value: Double,
    var isGlued: Boolean) {

    override fun toString(): String {
        return "model: ${bluePrint.friendlyName} | family: ${bluePrint.family} | rarity: ${bluePrint.rarity} | float: $weaponFloat | value: $$value"
    }
}