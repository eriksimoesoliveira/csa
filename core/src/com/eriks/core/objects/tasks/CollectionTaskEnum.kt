package com.eriks.core.objects.tasks

import com.eriks.core.objects.Family
import com.eriks.core.objects.Condition

enum class CollectionTaskEnum(val family: Family, val weaponFloat: Condition, val description: String = "No Description Yet", val reward: Int = 13) {
    KILOWATT_BS(Family.KILOWATT_CASE, Condition.BATTLE_SCARRED, "Complete Kilowatt Collection (BS)", 200),
    KILOWATT_WW(Family.KILOWATT_CASE, Condition.WELL_WORN, "Complete Kilowatt Collection (WW)", 400),
    KILOWATT_FT(Family.KILOWATT_CASE, Condition.FIELD_TESTED, "Complete Kilowatt Collection (FT)", 600),
    KILOWATT_MW(Family.KILOWATT_CASE, Condition.MINIMAL_WEAR, "Complete Kilowatt Collection (MW)", 800),
    KILOWATT_FN(Family.KILOWATT_CASE, Condition.FACTORY_NEW, "Complete Kilowatt Collection (FN)", 1000),

    REVO_BS(Family.REVOLUTION_CASE, Condition.BATTLE_SCARRED, "Complete Revolution Collection (BS)", 200),
    REVO_WW(Family.REVOLUTION_CASE, Condition.WELL_WORN, "Complete Revolution Collection (WW)", 400),
    REVO_FT(Family.REVOLUTION_CASE, Condition.FIELD_TESTED, "Complete Revolution Collection (FT)", 600),
    REVO_MW(Family.REVOLUTION_CASE, Condition.MINIMAL_WEAR, "Complete Revolution Collection (MW)", 800),
    REVO_FN(Family.REVOLUTION_CASE, Condition.FACTORY_NEW, "Complete Revolution Collection (FN)", 1000),

    RECOIL_BS(Family.RECOIL_CASE, Condition.BATTLE_SCARRED, "Complete Recoil Collection (BS)", 200),
    RECOIL_WW(Family.RECOIL_CASE, Condition.WELL_WORN, "Complete Recoil Collection (WW)", 400),
    RECOIL_FT(Family.RECOIL_CASE, Condition.FIELD_TESTED, "Complete Recoil Collection (FT)", 600),
    RECOIL_MW(Family.RECOIL_CASE, Condition.MINIMAL_WEAR, "Complete Recoil Collection (MW)", 800),
    RECOIL_FN(Family.RECOIL_CASE, Condition.FACTORY_NEW, "Complete Recoil Collection (FN)", 1000),

    DN_BS(Family.NIGHTMARE_CASE, Condition.BATTLE_SCARRED, "Complete D&N Collection (BS)", 200),
    DN_WW(Family.NIGHTMARE_CASE, Condition.WELL_WORN, "Complete D&N Collection (WW)", 400),
    DN_FT(Family.NIGHTMARE_CASE, Condition.FIELD_TESTED, "Complete D&N Collection (FT)", 600),
    DN_MW(Family.NIGHTMARE_CASE, Condition.MINIMAL_WEAR, "Complete D&N Collection (MW)", 800),
    DN_FN(Family.NIGHTMARE_CASE, Condition.FACTORY_NEW, "Complete D&N Collection (FN)", 1000),

    AGENTS1_BS(Family.AGENTS_1, Condition.BATTLE_SCARRED, "Complete Agents 1 Collection (BS)", 200),
    AGENTS1_WW(Family.AGENTS_1, Condition.WELL_WORN, "Complete Agents 1 Collection (WW)", 400),
    AGENTS1_FT(Family.AGENTS_1, Condition.FIELD_TESTED, "Complete Agents 1 Collection (FT)", 600),
    AGENTS1_MW(Family.AGENTS_1, Condition.MINIMAL_WEAR, "Complete Agents 1 Collection (MW)", 800),
    AGENTS1_FN(Family.AGENTS_1, Condition.FACTORY_NEW, "Complete Agents 1 Collection (FN)", 1000),

    MIRAGE_BS(Family.MIRAGE, Condition.BATTLE_SCARRED, "Complete Mirage Collection (BS)", 200),
    MIRAGE_WW(Family.MIRAGE, Condition.WELL_WORN, "Complete Mirage Collection (WW)", 400),
    MIRAGE_FT(Family.MIRAGE, Condition.FIELD_TESTED, "Complete Mirage Collection (FT)", 600),
    MIRAGE_MW(Family.MIRAGE, Condition.MINIMAL_WEAR, "Complete Mirage Collection (MW)", 800),
    MIRAGE_FN(Family.MIRAGE, Condition.FACTORY_NEW, "Complete Mirage Collection (FN)", 1000);

    companion object {
        fun getTasksForCollection(family: Family): List<CollectionTaskEnum> {
            return values().filter { it.family == family }
        }

        fun getTaskByCollectionAndFloat(family: Family, weaponFloat: Condition): CollectionTaskEnum {
            return values().first { it.family == family && it.weaponFloat == weaponFloat }
        }
    }
}