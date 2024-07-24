package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.objects.Card
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.dialogs.FullScreenCardDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class CardPlaceGroup(private val number: Int, card: Card?): Group() {

    init {

        if (card != null) {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    FullScreenCardDialog(card).show(stage)
                }
            })
        }

        if (card == null) {
            //Placeholder
            val st1 = ImageCache.getImage("wp2/placeholder.png")
            st1.width = 304f
            st1.height = 171f
            addActor(st1)

            //Number
            val numberLabel = Label(number.toString(), UIController.skin, "Roboto-Bold-28-White")
            UIUtil.centerBySize(numberLabel, st1.width, st1.height)
            addActor(numberLabel)
        } else {
            //Card
            val st1 = CardGroup(card, false, enableFullScreen = true, "")
            st1.width = 304f
            st1.height = 171f
            addActor(st1)

        }
    }
}