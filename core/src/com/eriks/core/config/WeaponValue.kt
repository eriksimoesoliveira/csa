package com.eriks.core.config

import com.eriks.core.objects.Rarity
import com.eriks.core.objects.CardBluePrint
import com.eriks.core.objects.Condition
import util.RandomUtil

object WeaponValue {

    fun generateCardValue(bluePrint: CardBluePrint, weaponFloat: Condition): Double {
        return when (bluePrint.rarity) {
            Rarity.MIL_SPEC     -> when (weaponFloat) {
                Condition.BATTLE_SCARRED  -> randomPercent(0.05)
                Condition.WELL_WORN       -> randomPercent(0.19)
                Condition.FIELD_TESTED    -> randomPercent(0.29)
                Condition.MINIMAL_WEAR    -> randomPercent(0.38)
                Condition.FACTORY_NEW     -> randomPercent(0.48)
            }
            Rarity.RESTRICTED   -> when (weaponFloat) {
                Condition.BATTLE_SCARRED  -> randomPercent(0.19)
                Condition.WELL_WORN       -> randomPercent(0.38)
                Condition.FIELD_TESTED    -> randomPercent(0.53)
                Condition.MINIMAL_WEAR    -> randomPercent(0.67)
                Condition.FACTORY_NEW     -> randomPercent(0.96)
            }
            Rarity.CLASSIFIED   -> when (weaponFloat) {
                Condition.BATTLE_SCARRED  -> randomPercent(0.96)
                Condition.WELL_WORN       -> randomPercent(4.8)
                Condition.FIELD_TESTED    -> randomPercent(14.4)
                Condition.MINIMAL_WEAR    -> randomPercent(19.2)
                Condition.FACTORY_NEW     -> randomPercent(28.8)
            }
            Rarity.COVERT       -> when (weaponFloat) {
                Condition.BATTLE_SCARRED  -> randomPercent(28.8)
                Condition.WELL_WORN       -> randomPercent(48.0)
                Condition.FIELD_TESTED    -> randomPercent(67.2)
                Condition.MINIMAL_WEAR    -> randomPercent(86.4)
                Condition.FACTORY_NEW     -> randomPercent(96.0)
            }
            Rarity.COVERT_RARE  -> when (weaponFloat) {
                Condition.BATTLE_SCARRED  -> randomPercent(96.0)
                Condition.WELL_WORN       -> randomPercent(115.2)
                Condition.FIELD_TESTED    -> randomPercent(153.6)
                Condition.MINIMAL_WEAR    -> randomPercent(172.8)
                Condition.FACTORY_NEW     -> randomPercent(192.0)
            }
        }
    }

    private fun randomPercent(value: Double): Double {
        val upOrDown = RandomUtil.randomElementFromList(listOf(1, -1))
        val percent = RandomUtil.randomInt(10)
        val finalPercent = upOrDown.toDouble()*percent.toDouble()
        val toDecimal = (100+finalPercent.toDouble())/100
        return value * toDecimal
    }

}