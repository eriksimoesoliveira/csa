package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.eriks.core.objects.Card
import com.eriks.core.ui.screen.v2.CardGroup
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.UIUtil

class FullScreenCardDialog(val card: Card): FullScreenDialog(false) {

    private lateinit var currentCard: CardGroup

    override fun show(stage: Stage?): Dialog {
        currentCard = CardGroup(card, true, enableFullScreen = false, "")
        addActor(currentCard)
        UIUtil.centerInScreen(currentCard)

        return super.show(stage)
    }
}