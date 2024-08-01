package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil
import kotlin.system.exitProcess

class WrongVersionDialog: FullScreenDialog(true, false) {

    private val label = Label("There's a new version available.\nPlease update your game before proceeding.", UIController.skin, "Roboto-Bold-45").apply {
        setAlignment(Align.center)
    }

    override fun show(stage: Stage?): Dialog {
        val table = Table()

        UIUtil.centerInScreen(label)

        val niceButton = ImageCache.getImage("ui/nicebutton.png")
        niceButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                exitProcess(0)
            }
        })

        table.add(label)
        table.row()
        table.add(niceButton).padTop(50f)
        table.pack()

        addActor(table)
        UIUtil.centerInScreen(table)

        return super.show(stage)
    }

    override fun closeButtonClicked() {

    }
}