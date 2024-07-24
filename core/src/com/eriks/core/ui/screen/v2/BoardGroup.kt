package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.eriks.core.GameController
import com.eriks.core.objects.Family
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class BoardGroup(private val family: Family): Group() {

    init {
        setSize(1500f, 1080f)
//        debug()

        val background = ImageCache.getImage(family.bgName)
        addActor(background)

        buildCards()

    }

    private fun buildCards() {
        val tbl = Table()

        var unitCounter = 1
        for (i in family.fromId..family.toId) {

            val card = GameController.albumCards[family]?.get(i)

            tbl.add(CardPlaceGroup(i, card)).width(304f).height(171f).padRight(30f).padBottom(30f)

            if (unitCounter++ % 4==0) {
                tbl.row()
            }
        }

        tbl.pack()

        UIUtil.centerByHeight(tbl, height)

        tbl.x = (width - tbl.width) / 2

        addActor(tbl)
    }

}