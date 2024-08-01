package com.eriks.core.config

import com.eriks.core.objects.CardBluePrint
import com.eriks.core.objects.Condition
import com.eriks.core.objects.Rarity

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

    fun generateRarityWhite(dice: Int): Rarity {
        return when (dice) {
            in 0..39 -> Rarity.RESTRICTED       //40%
            in 40..69 -> Rarity.CLASSIFIED      //30%
            in 70..94 -> Rarity.COVERT          //25%
            in 95..100 -> Rarity.COVERT_RARE    //5%
            else -> throw RuntimeException("Invalid dice value: $dice")
        }
    }

    fun getAllBluePrints():List<CardBluePrint> {
        return CardBluePrint.values().asList()
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

    fun generateRedFloat(dice: Int): Condition {
        return when (dice) {
            in 0..25 -> Condition.WELL_WORN         //25%
            in 26..60 -> Condition.FIELD_TESTED     //35%
            in 61..85 -> Condition.MINIMAL_WEAR     //25%
            in 86..100 -> Condition.FACTORY_NEW     //15%
            else -> throw RuntimeException("Invalid dice value $dice")
        }
    }

    const val regularDescription = "Float Rates:\n" +
            "\tBS -> 20%\n" +
            "\tWW -> 20%\n" +
            "\tFT -> 30%\n" +
            "\tMW -> 20%\n" +
            "\tFN -> 10%\n" +
            "\nRarity Rates:\n" +
            "\tBlue -> 40%\n" +
            "\tPurple -> 30%\n" +
            "\tPink -> 18%\n" +
            "\tRed -> 10%\n" +
            "\tOrange -> 2%"

    const val redDescription = "Float Rates:\n" +
            "\tBS -> 0%\n" +
            "\tWW -> 25%\n" +
            "\tFT -> 35%\n" +
            "\tMW -> 25%\n" +
            "\tFN -> 15%\n" +
            "\nRarity Rates:\n" +
            "\tBlue -> 40%\n" +
            "\tPurple -> 30%\n" +
            "\tPink -> 18%\n" +
            "\tRed -> 10%\n" +
            "\tOrange -> 2%"

    const val whiteDescription = "Float Rates:\n" +
            "\tBS -> 0%\n" +
            "\tWW -> 25%\n" +
            "\tFT -> 35%\n" +
            "\tMW -> 25%\n" +
            "\tFN -> 15%\n" +
            "\nRarity Rates:\n" +
            "\tBlue -> 0%\n" +
            "\tPurple -> 40%\n" +
            "\tPink -> 30%\n" +
            "\tRed -> 25%\n" +
            "\tOrange -> 5%"
}