package com.eriks.core.objects

enum class Condition(val abbreviation: String, val friendlyName: String, val level: Int) {
    BATTLE_SCARRED("BS", "Battle Scarred", 1),
    WELL_WORN("WW", "Well Worn", 2),
    FIELD_TESTED("FT", "Field Tested", 3),
    MINIMAL_WEAR("MW", "Minimal Wear", 4),
    FACTORY_NEW("FN", "Factory New", 5),
}