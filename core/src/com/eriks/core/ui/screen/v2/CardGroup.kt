package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.objects.Card
import com.eriks.core.objects.Condition
import com.eriks.core.objects.Rarity
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.dialogs.FullScreenCardDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class CardGroup(val card: Card, isBig: Boolean, enableFullScreen: Boolean = false, val info: String?, var valueVisible: Boolean) : Group() {

    private val bigWidth = 1500f
    private val bigHeight = 844f
    private val smallWidth = 304f
    private val smallHeight = 171f
    lateinit var valueLabel: Label

    init {
        if (isBig) {
            renderCard(bigWidth, bigHeight, true)
        } else {
            renderCard(smallWidth, smallHeight, false)
        }

        if (enableFullScreen) {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    FullScreenCardDialog(card).show(stage)
                }
            })
        }
    }

    private fun renderCard(w: Float, h: Float, isBig: Boolean) {
        setSize(w, h)
        if (card.bluePrint.fullImage) {
            renderFullImageCard(w, h)
        } else {
            renderRegularCard(w, h, isBig)
        }
    }

    private fun renderFullImageCard(w: Float, h: Float) {
        val image = ImageCache.getImage(card.bluePrint.imageName)
        image.width = w
        image.height = h
        UIUtil.centerByWidth(image, w)
        addActor(image)
        addMask(w, h)
        addValueLabel(w, h, isBig = false)
        addInfoLabel(false)
    }

    private fun renderRegularCard(w: Float, h: Float, isBig: Boolean) {
        val backgroundImage = getBackgroundImage()
        val bg = ImageCache.getImage(backgroundImage)
        bg.width = w
        bg.height = h
        addActor(bg)

        addCollectionIcon(w, h, isBig)
        addSkinNameLabel(w, h, isBig)
        addWeaponImage(w, h)
        addMask(w, h)
        addValueLabel(w, h, isBig)
        addInfoLabel(isBig)
    }

    private fun getBackgroundImage(): String {
        return when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> "wp2/BLUE-BACKGROUND.png"
            Rarity.RESTRICTED -> "wp2/PURPLE-BACKGROUND.png"
            Rarity.CLASSIFIED -> "wp2/PINK-BACKGROUND.png"
            Rarity.COVERT -> "wp2/RED-BACKGROUND.png"
            Rarity.COVERT_RARE -> "wp2/ORANGE-BACKGROUND.png"
        }
    }

    private fun getMaskImage(): String {
        return when (card.weaponFloat) {
            Condition.BATTLE_SCARRED -> "wp2/BS-MASK-2.png"
            Condition.WELL_WORN -> "wp2/WW-MASK-2.png"
            Condition.FIELD_TESTED -> "wp2/FT-MASK-2.png"
            Condition.MINIMAL_WEAR -> "wp2/MW-MASK-2.png"
            Condition.FACTORY_NEW -> "wp2/FN-MASK-2.png"
        }
    }

    private fun addMask(w: Float, h: Float) {
        val mask = ImageCache.getImage(getMaskImage())
        mask.width = w
        mask.height = h
        addActor(mask)
    }

    private fun addCollectionIcon(w: Float, h: Float, isBig: Boolean) {
        val colIcon = ImageCache.getImage(card.bluePrint.family.iconName)
        colIcon.setSize(if (isBig) 110f else 20f, if (isBig) 110f else 20f)
        addActor(colIcon)
        UIUtil.align_topRightInParent(colIcon, if (isBig) 35f else 8f, if (isBig) 40f else 9f)
    }

    private fun addSkinNameLabel(w: Float, h: Float, isBig: Boolean) {
        val fontName = when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> if (isBig) "BLUE-64" else "BLUE-14"
            Rarity.RESTRICTED -> if (isBig) "PURPLE-64" else "PURPLE-14"
            Rarity.CLASSIFIED -> if (isBig) "PINK-64" else "PINK-14"
            Rarity.COVERT -> if (isBig) "RED-64" else "RED-14"
            Rarity.COVERT_RARE -> if (isBig) "ORANGE-64" else "ORANGE-14"
        }

        val skinNameLabel = Label(card.bluePrint.friendlyName, UIController.skin, fontName)
        addActor(skinNameLabel)
        UIUtil.align_centerBottomInParent(skinNameLabel, if (isBig) 85f else 18f)
    }

    private fun addWeaponImage(w: Float, h: Float) {
        val weapon = ImageCache.getImage(card.bluePrint.imageName)
        weapon.width = w * 0.7f
        weapon.height = h * 0.7f
        weapon.y = weapon.y + h / 4
        UIUtil.centerByWidth(weapon, w)
        addActor(weapon)
    }

    private fun addValueLabel(w: Float, h: Float, isBig: Boolean) {
        valueLabel = Label(valueLabelTxt(), UIController.skin, if (isBig) "YELLOW-64-WB" else "YELLOW-14-WB")
        valueLabel.isVisible = valueVisible
        addActor(valueLabel)
    }

    private fun addInfoLabel(isBig: Boolean) {
        val infoLabel = Label(info, UIController.skin, if (isBig) "GREEN-40-WB" else "GREEN-14-WB")
        addActor(infoLabel)
        UIUtil.align_centerTopInParent(infoLabel)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        valueLabel.isVisible = valueVisible
        super.draw(batch, parentAlpha)
    }

    private fun valueLabelTxt() = "${UIUtil.decimalFormat.format(card.value)} (${card.weaponFloat.abbreviation})"
}