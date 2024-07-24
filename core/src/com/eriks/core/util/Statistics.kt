package com.eriks.core.util

import com.eriks.core.objects.Family
import com.eriks.core.objects.Rarity
import com.eriks.core.objects.Card
import com.eriks.core.objects.Condition

object Statistics {

    private var packagesOpen = 0
    private var cardsCreated = 0
    private var rarities: MutableMap<Rarity, Int> = mutableMapOf()
    private var collection: MutableMap<Family, Int> = mutableMapOf()
    private var float: MutableMap<Condition, Int> = mutableMapOf()

    fun printStatistics() {
        println("-------------------Statistics------------------------------")
        println("Packages Open: $packagesOpen")
        println("Cards Created: $cardsCreated")
        rarities.forEach { (t, u) -> println("R%% ${t.level}-$t: $u (${((u.toDouble()/ cardsCreated.toDouble())*100).toInt()}%)") }
        collection.forEach { (t, u) -> println("C%% $t: $u (${((u.toDouble()/ cardsCreated.toDouble())*100).toInt()}%)") }
        float.forEach { (t, u) -> println("F%% ${t.level}-$t: $u (${((u.toDouble()/ cardsCreated.toDouble())*100).toInt()}%)") }
    }

    fun packageOpen() {
        packagesOpen++
    }

    fun cardCreated(card: Card) {
        cardsCreated++
        if (rarities[card.bluePrint.rarity] == null) {
            rarities[card.bluePrint.rarity] = 1
        } else {
            rarities[card.bluePrint.rarity] = rarities[card.bluePrint.rarity]!! + 1
        }

        if (collection[card.bluePrint.family] == null) {
            collection[card.bluePrint.family] = 1
        } else {
            collection[card.bluePrint.family] = collection[card.bluePrint.family]!! + 1
        }

        if (float[card.weaponFloat] == null) {
            float[card.weaponFloat] = 1
        } else {
            float[card.weaponFloat] = float[card.weaponFloat]!! + 1
        }
    }

}