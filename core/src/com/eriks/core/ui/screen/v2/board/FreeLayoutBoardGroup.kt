package com.eriks.core.ui.screen.v2.board

import com.eriks.core.GameController
import com.eriks.core.objects.Family
import com.eriks.core.ui.screen.v2.CardPlaceGroup
import com.eriks.core.ui.screen.v2.PlaceHolderEnum

class FreeLayoutBoardGroup(private val family: Family): BoardGroup(family) {

    override fun buildCards(family: Family) {
        for (i in family.fromId..family.toId) {
            val card = GameController.albumCards[family]?.get(i)
            val cardPlaceGroup = when (i) {
                101 -> CardPlaceGroup(i, card, PlaceHolderEnum.FULL).apply {
                    x = 110f
                    y = 850f
                }
                102 -> CardPlaceGroup(i, card, PlaceHolderEnum.FULL).apply {
                    x = 110f
                    y = 650f
                }
                103 -> CardPlaceGroup(i, card, PlaceHolderEnum.FULL).apply {
                    x = 110f
                    y = 450f
                }
                104 -> CardPlaceGroup(i, card, PlaceHolderEnum.FULL).apply {
                    x = 110f
                    y = 250f
                }
                105 -> CardPlaceGroup(i, card, PlaceHolderEnum.FULL).apply {
                    x = 110f
                    y = 50f
                }

                //MOSAICO
                106 -> CardPlaceGroup(i, card, PlaceHolderEnum.LT).apply {
                    x = 500f
                    y = 850f
                }
                107 -> CardPlaceGroup(i, card, PlaceHolderEnum.T).apply {
                    x = 804f
                    y = 850f
                }
                108 -> CardPlaceGroup(i, card, PlaceHolderEnum.RT).apply {
                    x = 1108f
                    y = 850f
                }
                109 -> CardPlaceGroup(i, card, PlaceHolderEnum.L).apply {
                    x = 500f
                    y = 850f - 171f
                }
                110 -> CardPlaceGroup(i, card, PlaceHolderEnum.EMPTY).apply {
                    x = 500f + 304f
                    y = 850f - 171f
                }
                111 -> CardPlaceGroup(i, card, PlaceHolderEnum.R).apply {
                    x = 500f + 304f + 304f
                    y = 850f - 171f
                }
                112 -> CardPlaceGroup(i, card, PlaceHolderEnum.L).apply {
                    x = 500f
                    y = 850f - 171f - 171f
                }
                113 -> CardPlaceGroup(i, card, PlaceHolderEnum.EMPTY).apply {
                    x = 500f + 304f
                    y = 850f - 171f - 171f
                }
                114 -> CardPlaceGroup(i, card, PlaceHolderEnum.R).apply {
                    x = 500f + 304f + 304f
                    y = 850f - 171f - 171f
                }
                115 -> CardPlaceGroup(i, card, PlaceHolderEnum.LB).apply {
                    x = 500f
                    y = 850f - 171f - 171f - 171f
                }
                116 -> CardPlaceGroup(i, card, PlaceHolderEnum.B).apply {
                    x = 500f + 304f
                    y = 850f - 171f - 171f - 171f
                }
                117 -> CardPlaceGroup(i, card, PlaceHolderEnum.RB).apply {
                    x = 500f + 304f + 304f
                    y = 850f - 171f - 171f - 171f
                }

                //AWP
                118 -> CardPlaceGroup(i, card, PlaceHolderEnum.LTB).apply {
                    x = 500f
                    y = 100f
                }
                119 -> CardPlaceGroup(i, card, PlaceHolderEnum.TB).apply {
                    x = 500f + 304f
                    y = 100f
                }
                120 -> CardPlaceGroup(i, card, PlaceHolderEnum.RTB).apply {
                    x = 500f + 304f + 304f
                    y = 100f
                }



                else -> throw Exception("Error rendering FreeLayoutBoard")
            }
            addActor(cardPlaceGroup)
        }
    }

}