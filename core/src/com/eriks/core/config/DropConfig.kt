package com.eriks.core.config

import com.eriks.core.objects.Family
import com.eriks.core.objects.Rarity
import com.eriks.core.objects.CardBluePrint
import com.eriks.core.objects.Condition

object DropConfig {

    fun generateRarity(dice: Int): Rarity {
        return when (dice) {
            in 0..39 -> Rarity.MIL_SPEC         //40%
            in 40..69 -> Rarity.RESTRICTED      //30%
            in 70..87 -> Rarity.CLASSIFIED      //18%
            in 88..97 -> Rarity.COVERT          //10%
            in 98..100 -> Rarity.COVERT_RARE    //2%
            else -> throw RuntimeException("Invalid dice value: $dice")
        }
    }

    fun getAllCollections(): Map<Family, List<CardBluePrint>> {
        val ret = mutableMapOf<Family, List<CardBluePrint>>()
        ret[Family.KILOWATT_CASE] = getAllBluePrintsOfCollection(Family.KILOWATT_CASE)
        ret[Family.REVOLUTION_CASE] = getAllBluePrintsOfCollection(Family.REVOLUTION_CASE)
        return ret
    }

    fun getAllBluePrints():List<CardBluePrint> {
        return CardBluePrint.values().asList()
    }

    fun getAllBluePrintsOfCollection(collection: Family):List<CardBluePrint> {
        return CardBluePrint.values().asList().filter { it.family == collection }
    }

    fun generateFloat(dice: Int): Condition {
        return when (dice) {
            in 0..20 -> Condition.BATTLE_SCARRED    //20%
            in 21..40 -> Condition.WELL_WORN        //20%
            in 41..69 -> Condition.FIELD_TESTED     //30%
            in 70..90 -> Condition.MINIMAL_WEAR     //20%
            in 91..100 -> Condition.FACTORY_NEW     //10%
            else -> throw RuntimeException("Invalid dice value $dice")
        }
    }
}