package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.GameController
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.Spinner
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class ShopDialog: FullScreenDialog(false) {

    private val packagePrice = 300.0
    private var total = 300.0
    private var totalFormatted = "TOTAL: " + UIUtil.decimalFormat.format(300)
    private lateinit var totalLabel: Label
    private lateinit var spinner: Spinner

    override fun show(stage: Stage?): Dialog {

        val template = ImageCache.getImage("ui/storetemplate.png")
        addActor(template)
        UIUtil.centerInScreen(template)

        val packIcon = ImageCache.getImage("ui/package2.png")
        UIUtil.resizeProportional(100f, packIcon)
        packIcon.x = 600f
        packIcon.y = 700f
        addActor(packIcon)

        val packNameLabel = Label("Cards Package", UIController.skin, "Roboto-Bold-28-White")
        packNameLabel.x = 730f
        packNameLabel.y = 780f
        addActor(packNameLabel)

        val packValueLabel = Label("${UIUtil.decimalFormat.format(packagePrice)}", UIController.skin, "Roboto-Bold-45")
        packValueLabel.x = 730f
        packValueLabel.y = 730f
        addActor(packValueLabel)

        spinner = Spinner(10, ::spinnerCallBack, 1)
        spinner.x = 1100f
        spinner.y = 730f
        addActor(spinner)

        totalLabel = Label("TOTAL: 1.000,00", UIController.skin, "Roboto-Bold-45")
        totalLabel.width = 500f
        totalLabel.x = 830f
        totalLabel.y = 300f
        totalLabel.setAlignment(Align.right)
        addActor(totalLabel)

        val finishButton = ImageCache.getImage("ui/finishbutton.png")
        addActor(finishButton)
        UIUtil.centerByWidth(finishButton, 1920f)
        finishButton.y = 150f
        finishButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (GameController.playerCash >= total) {
                    GameController.purchase(spinner.value, total)
                    hide(null)
                }
            }
        })

        return super.show(stage)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        totalLabel.setText(totalFormatted)

        super.draw(batch, parentAlpha)
    }

    private fun spinnerCallBack(added: Boolean) {
        total = spinner.value * packagePrice
        totalFormatted = "TOTAL: " + UIUtil.decimalFormat.format(spinner.value * packagePrice)
    }

}