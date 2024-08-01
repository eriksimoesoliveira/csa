package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.GameController
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class NewPackageDialog: FullScreenDialog(true, false) {

    private val label = Label("You have x new packages!", UIController.skin, "Roboto-Bold-45")

    override fun show(stage: Stage?): Dialog {
        val table = Table()

        val txt = if (GameController.packsToAlert == 1) {
            "You received ${GameController.packsToAlert} Card Pack!"
        } else {
            "You received ${GameController.packsToAlert} Card Packs!"
        }

        label.setText(txt)
        GameController.packsToAlert = 0
        UIUtil.centerInScreen(label)

        val packIcon = ImageCache.getImage("ui/package2.png").apply {
            width = 100f
            height = 200f
        }

        val niceButton = ImageCache.getImage("ui/nicebutton.png")
        niceButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                hide(null)
            }
        })

        table.add(label)
        table.row()
        table.add(packIcon).width(130f).height(200f).padTop(30f)
        table.row()
        table.add(niceButton).padTop(50f)
        table.pack()

        addActor(table)
        UIUtil.centerInScreen(table)

        return super.show(stage)
    }

}