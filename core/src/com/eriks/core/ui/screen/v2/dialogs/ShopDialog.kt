package com.eriks.core.ui.screen.v2.dialogs

import InformationBox
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.GameController
import com.eriks.core.config.DropConfig
import com.eriks.core.config.GeneralConfigs
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.Spinner
import com.eriks.core.ui.util.BlurredSquare
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class ShopDialog: FullScreenDialog(false) {

    private var total = 0.0
    private var totalFormatted = "TOTAL: " + UIUtil.decimalFormat.format(0)
    private lateinit var totalLabel: Label
    private lateinit var spinner: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner
    private lateinit var infoBox: InformationBox

    override fun show(stage: Stage?): Dialog {

        val template = ImageCache.getImage("ui/storetemplate.png")
        addActor(template)
        UIUtil.centerInScreen(template)

        //Pack1
        val packIcon = ImageCache.getImage("ui/package2.png")
        UIUtil.resizeProportional(100f, packIcon)
        packIcon.x = 600f
        packIcon.y = 700f
        addActor(packIcon)

        val packNameLabel = Label("Regular Cards Package", UIController.skin, "Roboto-Bold-28-White")
        packNameLabel.x = 730f
        packNameLabel.y = 780f
        addActor(packNameLabel)

        val packValueLabel = Label("${UIUtil.decimalFormat.format(GeneralConfigs.REGULAR_PACKAGE_PRICE)}", UIController.skin, "Roboto-Bold-45")
        packValueLabel.x = 730f
        packValueLabel.y = 730f
        addActor(packValueLabel)

        spinner = Spinner(10, ::spinnerCallBack, 0)
        spinner.x = 1100f
        spinner.y = 730f
        addActor(spinner)

        //Pack2
        val packIcon2 = ImageCache.getImage("ui/package-red.png")
        UIUtil.resizeProportional(100f, packIcon2)
        packIcon2.x = 600f
        packIcon2.y = 550f
        addActor(packIcon2)

        val pack2NameLabel = Label("WW Cards Package", UIController.skin, "Roboto-Bold-28-White")
        pack2NameLabel.x = 730f
        pack2NameLabel.y = 630f
        addActor(pack2NameLabel)

        val pack2ValueLabel = Label("${UIUtil.decimalFormat.format(GeneralConfigs.RED_PACKAGE_PRICE)}", UIController.skin, "Roboto-Bold-45")
        pack2ValueLabel.x = 730f
        pack2ValueLabel.y = 580f
        addActor(pack2ValueLabel)

        spinner2 = Spinner(10, ::spinnerCallBack, 0)
        spinner2.x = 1100f
        spinner2.y = 580f
        addActor(spinner2)

        //Pack3
        val packIcon3 = ImageCache.getImage("ui/package-white.png")
        UIUtil.resizeProportional(100f, packIcon3)
        packIcon3.x = 600f
        packIcon3.y = 400f
        addActor(packIcon3)

        val pack3NameLabel = Label("Pink Cards Package", UIController.skin, "Roboto-Bold-28-White")
        pack3NameLabel.x = 730f
        pack3NameLabel.y = 480f
        addActor(pack3NameLabel)

        val pack3ValueLabel = Label("${UIUtil.decimalFormat.format(GeneralConfigs.WHITE_PACKAGE_PRICE)}", UIController.skin, "Roboto-Bold-45")
        pack3ValueLabel.x = 730f
        pack3ValueLabel.y = 430f
        addActor(pack3ValueLabel)

        spinner3 = Spinner(10, ::spinnerCallBack, 0)
        spinner3.x = 1100f
        spinner3.y = 430f
        addActor(spinner3)

        if (GameController.albumValue < GeneralConfigs.RED_PACKAGE_UNBLOCK_VALUE) {
            addActor(BlurredSquare(600f, 550f, 720f, 150f))
            addActor(Label("Blocked (AV ${UIUtil.decimalFormat.format(GeneralConfigs.RED_PACKAGE_UNBLOCK_VALUE)})", UIController.skin, "Roboto-Bold-28-White").apply {
                x = 850f
                y = 580f
            })
        }

        if (GameController.albumValue < GeneralConfigs.WHITE_PACKAGE_UNBLOCK_VALUE) {
            addActor(BlurredSquare(600f, 400f, 720f, 150f))
            addActor(Label("Blocked (AV ${UIUtil.decimalFormat.format(GeneralConfigs.WHITE_PACKAGE_UNBLOCK_VALUE)})", UIController.skin, "Roboto-Bold-28-White").apply {
                x = 850f
                y = 460f
            })
        }

        total = 0.0
        totalFormatted = "TOTAL: " + UIUtil.decimalFormat.format(total)

        totalLabel = Label(totalFormatted, UIController.skin, "Roboto-Bold-45")
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
                if (GameController.playerCash >= total && total > 0) {
                    GameController.purchase(spinner.value, spinner2.value, spinner3.value, total)
                    hide(null)
                }
            }
        })

        infoBox = InformationBox(UIController.skin)
        addActor(infoBox)

        // Add hover and click listeners to pack icons
        addHoverAndClickListener(packIcon, DropConfig.regularDescription)
        addHoverAndClickListener(packIcon2, DropConfig.redDescription)
        addHoverAndClickListener(packIcon3, DropConfig.whiteDescription)

        return super.show(stage)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        totalLabel.setText(totalFormatted)

        super.draw(batch, parentAlpha)
    }

    private fun spinnerCallBack(added: Boolean) {
        total = (spinner.value * GeneralConfigs.REGULAR_PACKAGE_PRICE) + (spinner2.value * GeneralConfigs.RED_PACKAGE_PRICE) + (spinner3.value * GeneralConfigs.WHITE_PACKAGE_PRICE)
        totalFormatted = "TOTAL: " + UIUtil.decimalFormat.format(total)
    }

    private fun addHoverAndClickListener(packIcon: Actor, infoText: String) {
        packIcon.addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                infoBox.setText(infoText)
                infoBox.setPosition(packIcon.x + packIcon.width, 500f)
                infoBox.isVisible = true
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                infoBox.isVisible = false
            }

            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                infoBox.setText(infoText)
                infoBox.setPosition(packIcon.x + packIcon.width, 500f)
                infoBox.isVisible = true
                return true
            }
        })
    }
}