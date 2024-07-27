package com.eriks.core.ui.screen.v2

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

class CardGroup(val card: Card, isBig: Boolean, enableFullScreen: Boolean = false, val info: String?): Group() {

    private val bigWidth = 1500f
    private val bigHeight = 844f

    private val smallWidth = 304f
    private val smallHeight = 171f

    init {
        if (isBig) {
            if (card.bluePrint.fullImage) {
                renderBigFull(bigWidth, bigHeight)
            } else {
                renderBig(bigWidth, bigHeight)
            }
        } else {
            if (card.bluePrint.fullImage) {
                renderSmallFull(smallWidth, smallHeight)
            } else {
                renderSmall(smallWidth, smallHeight)
            }
        }

        if (enableFullScreen) {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    FullScreenCardDialog(card).show(stage)
                }
            })
        }

    }

    private fun renderBigFull(w: Float, h: Float) {
        setSize(w, h)

        val image = ImageCache.getImage(card.bluePrint.imageName)
        image.width = w
        image.height = h
        UIUtil.centerByWidth(image, w)
        addActor(image)

        //Rarity Mask
        val maskImage = when (card.weaponFloat) {
            Condition.BATTLE_SCARRED -> "wp2/BS-MASK-2.png"
            Condition.WELL_WORN -> "wp2/WW-MASK-2.png"
            Condition.FIELD_TESTED -> "wp2/FT-MASK-2.png"
            Condition.MINIMAL_WEAR -> "wp2/MW-MASK-2.png"
            Condition.FACTORY_NEW -> "wp2/FN-MASK-2.png"
        }
        val mask = ImageCache.getImage(maskImage)
        mask.width = w
        mask.height = h
        addActor(mask)

        //Value
        val valueLabel = Label(UIUtil.decimalFormat.format(card.value), UIController.skin, "YELLOW-64-WB")
        addActor(valueLabel)

        //Info
        val infoLabel = Label(info, UIController.skin, "GREEN-40-WB")
        addActor(infoLabel)
        UIUtil.align_centerTopInParent(infoLabel)
    }

    private fun renderBig(w: Float, h: Float) {
        setSize(w, h)

        //Background
        val backgroundImage = when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> "wp2/BLUE-BACKGROUND.png"
            Rarity.RESTRICTED -> "wp2/PURPLE-BACKGROUND.png"
            Rarity.CLASSIFIED -> "wp2/PINK-BACKGROUND.png"
            Rarity.COVERT -> "wp2/RED-BACKGROUND.png"
            Rarity.COVERT_RARE -> "wp2/ORANGE-BACKGROUND.png"
        }
        val bg = ImageCache.getImage(backgroundImage)
        bg.width = w
        bg.height = h
        addActor(bg)

        //Collection icon
        val colIcon = ImageCache.getImage(card.bluePrint.family.iconName)
        colIcon.setSize(110f, 110f)
        addActor(colIcon)
        UIUtil.align_topRightInParent(colIcon, 35f, 40f)

        val fontName = when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> "BLUE-64"
            Rarity.RESTRICTED -> "PURPLE-64"
            Rarity.CLASSIFIED -> "PINK-64"
            Rarity.COVERT -> "RED-64"
            Rarity.COVERT_RARE -> "ORANGE-64"
        }

        //Skin Name
        val floatLabel = Label(card.weaponFloat.friendlyName, UIController.skin, fontName)
        addActor(floatLabel)
        UIUtil.align_centerBottomInParent(floatLabel, 130f)

        val skinNameLabel = Label(card.bluePrint.friendlyName, UIController.skin, fontName)
        addActor(skinNameLabel)
        UIUtil.align_centerBottomInParent(skinNameLabel, 40f)

        val weapon = ImageCache.getImage(card.bluePrint.imageName)
        weapon.width = w * 0.7f
        weapon.height = h * 0.7f
        weapon.y = weapon.y + h/4
        UIUtil.centerByWidth(weapon, w)
        addActor(weapon)

        //Rarity Mask
        val maskImage = when (card.weaponFloat) {
            Condition.BATTLE_SCARRED -> "wp2/BS-MASK-2.png"
            Condition.WELL_WORN -> "wp2/WW-MASK-2.png"
            Condition.FIELD_TESTED -> "wp2/FT-MASK-2.png"
            Condition.MINIMAL_WEAR -> "wp2/MW-MASK-2.png"
            Condition.FACTORY_NEW -> "wp2/FN-MASK-2.png"
        }
//        if (card.weaponFloat != Condition.FACTORY_NEW) {
            val mask = ImageCache.getImage(maskImage)
            mask.width = w
            mask.height = h
            addActor(mask)
//        }

        //Value
        val valueLabel = Label(UIUtil.decimalFormat.format(card.value), UIController.skin, "YELLOW-64-WB")
        addActor(valueLabel)

        //Info
        val infoLabel = Label(info, UIController.skin, "GREEN-40-WB")
        addActor(infoLabel)
        UIUtil.align_centerTopInParent(infoLabel)
    }

    private fun renderSmall(w: Float, h: Float) {
        setSize(w, h)

        //Background
        val backgroundImage = when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> "wp2/BLUE-BACKGROUND.png"
            Rarity.RESTRICTED -> "wp2/PURPLE-BACKGROUND.png"
            Rarity.CLASSIFIED -> "wp2/PINK-BACKGROUND.png"
            Rarity.COVERT -> "wp2/RED-BACKGROUND.png"
            Rarity.COVERT_RARE -> "wp2/ORANGE-BACKGROUND.png"
        }
        val bg = ImageCache.getImage(backgroundImage)
        bg.width = w
        bg.height = h
        addActor(bg)

        //Collection icon
        val colIcon = ImageCache.getImage(card.bluePrint.family.iconName)
        colIcon.setSize(20f, 20f)
        addActor(colIcon)
        UIUtil.align_topRightInParent(colIcon, 8f, 9f)

        val fontName = when (card.bluePrint.rarity) {
            Rarity.MIL_SPEC -> "BLUE-14"
            Rarity.RESTRICTED -> "PURPLE-14"
            Rarity.CLASSIFIED -> "PINK-14"
            Rarity.COVERT -> "RED-14"
            Rarity.COVERT_RARE -> "ORANGE-14"
        }

        //Skin Name
        val floatLabel = Label(card.weaponFloat.friendlyName, UIController.skin, fontName)
        addActor(floatLabel)
        UIUtil.align_centerBottomInParent(floatLabel, 25f)

        val skinNameLabel = Label(card.bluePrint.friendlyName, UIController.skin, fontName)
        addActor(skinNameLabel)
        UIUtil.align_centerBottomInParent(skinNameLabel, 10f)

        val weapon = ImageCache.getImage(card.bluePrint.imageName)
        weapon.width = w * 0.7f
        weapon.height = h * 0.7f
        weapon.y = weapon.y + h/4
        UIUtil.centerByWidth(weapon, w)
        addActor(weapon)

        //Rarity Mask
        val maskImage = when (card.weaponFloat) {
            Condition.BATTLE_SCARRED -> "wp2/BS-MASK-2.png"
            Condition.WELL_WORN -> "wp2/WW-MASK-2.png"
            Condition.FIELD_TESTED -> "wp2/FT-MASK-2.png"
            Condition.MINIMAL_WEAR -> "wp2/MW-MASK-2.png"
            Condition.FACTORY_NEW -> "wp2/FN-MASK-2.png"
        }
//        if (card.weaponFloat != Condition.FACTORY_NEW) {
            val mask = ImageCache.getImage(maskImage)
            mask.width = w
            mask.height = h
            addActor(mask)
//        }

        //Value
        val valueLabel = Label(UIUtil.decimalFormat.format(card.value), UIController.skin, "YELLOW-14-WB")
        addActor(valueLabel)

        //Info
        val infoLabel = Label(info, UIController.skin, "GREEN-14-WB")
        addActor(infoLabel)
        UIUtil.align_centerTopInParent(infoLabel)
    }

    private fun renderSmallFull(w: Float, h: Float) {
        setSize(w, h)

        val image = ImageCache.getImage(card.bluePrint.imageName)
        image.width = w
        image.height = h
        UIUtil.centerByWidth(image, w)
        addActor(image)

        //Rarity Mask
        val maskImage = when (card.weaponFloat) {
            Condition.BATTLE_SCARRED -> "wp2/BS-MASK-2.png"
            Condition.WELL_WORN -> "wp2/WW-MASK-2.png"
            Condition.FIELD_TESTED -> "wp2/FT-MASK-2.png"
            Condition.MINIMAL_WEAR -> "wp2/MW-MASK-2.png"
            Condition.FACTORY_NEW -> "wp2/FN-MASK-2.png"
        }
        val mask = ImageCache.getImage(maskImage)
        mask.width = w
        mask.height = h
        addActor(mask)

        //Value
        val valueLabel = Label(UIUtil.decimalFormat.format(card.value), UIController.skin, "YELLOW-14-WB")
        addActor(valueLabel)

        //Info
        val infoLabel = Label(info, UIController.skin, "GREEN-14-WB")
        addActor(infoLabel)
        UIUtil.align_centerTopInParent(infoLabel)
    }

}