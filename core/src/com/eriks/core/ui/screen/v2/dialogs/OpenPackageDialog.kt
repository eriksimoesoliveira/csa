package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.GameController
import com.eriks.core.objects.Card
import com.eriks.core.objects.CardPackage
import com.eriks.core.ui.UIController
import com.eriks.core.ui.screen.v2.CardGroup
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OpenPackageDialog: FullScreenDialog(true) {

    lateinit var packageIcon: Image
    lateinit var cardPackage: CardPackage

    override fun show(stage: Stage?): Dialog {

        initializeContent()

        return super.show(stage)
    }

    private fun initializeContent() {
        packageIcon = when (cardPackage.type) {
            CardPackage.Type.REGULAR -> ImageCache.getImage("ui/package2.png")
            CardPackage.Type.RED -> ImageCache.getImage("ui/package-red.png")
            CardPackage.Type.WHITE -> ImageCache.getImage("ui/package-white.png")
        }

        UIUtil.resizeProportional(300f, packageIcon)

        packageIcon.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                CoroutineScope(Dispatchers.Main).launch {
                    val cards = GameController.openPackage(cardPackage)
                    Gdx.app.postRunnable {
                        openPackageAnimation1(cards)
                    }
                }
            }
        })
        UIUtil.centerBySize(packageIcon, prefWidth, prefHeight)
        addActor(packageIcon)

        val descriptionLabel = if (cardPackage.description.contains("VICTORY")) {
            Label(cardPackage.description, UIController.skin,  "GREEN-14-WB")
        } else {
            Label(cardPackage.description, UIController.skin,  "ORANGE-14")
        }
        UIUtil.centerInScreen(descriptionLabel)
        descriptionLabel.y -= 300f
        addActor(descriptionLabel)
    }

    private fun openPackageAnimation1(cards: List<Card>) {
        packageIcon.remove()

        val packageOpenedIcon = when (cardPackage.type) {
            CardPackage.Type.REGULAR -> ImageCache.getImage("ui/package2-open.png")
            CardPackage.Type.RED -> ImageCache.getImage("ui/package-red-open.png")
            CardPackage.Type.WHITE -> ImageCache.getImage("ui/package-white-open.png")
        }

        packageOpenedIcon.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.moveTo(packageOpenedIcon.x, -500f, .5f),
            Actions.removeActor(packageOpenedIcon)
        ))

        val card1 = CardGroup(cards[0], false, false, GameController.getCardInfo(cards[0]), true)
        card1.rotateBy(90f)
        card1.x = 810 + 230f
        card1.y = 320 + 180f
        addActor(card1)

        val card2 = CardGroup(cards[1], false, false, GameController.getCardInfo(cards[1]), true)
        card2.rotateBy(90f)
        card2.x = 810 + 230f
        card2.y = 320 + 160f
        addActor(card2)

        val card3 = CardGroup(cards[2], false, false, GameController.getCardInfo(cards[2]), true)
        card3.rotateBy(90f)
        card3.x = 810 + 230f
        card3.y = 320 + 140f
        addActor(card3)

        val card4 = CardGroup(cards[3], false, false, GameController.getCardInfo(cards[3]), true)
        card4.rotateBy(90f)
        card4.x = 810 + 230f
        card4.y = 320 + 120f
        addActor(card4)

        val card5 = CardGroup(cards[4], false, false, GameController.getCardInfo(cards[4]), true)
        card5.rotateBy(90f)
        card5.x = 810 + 230f
        card5.y = 320 + 100f
        addActor(card5)

        card5.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.parallel(
                Actions.rotateBy(-90f, .3f),
                Actions.moveTo(575f, 600f, .3f)
            )
        ))

        card4.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.parallel(
                Actions.rotateBy(-90f, .3f),
                Actions.moveTo(975f, 600f, .3f)
            )
        ))

        card3.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.parallel(
                Actions.rotateBy(-90f, .3f),
                Actions.moveTo(375f, 350f, .3f)
            )
        ))

        card2.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.parallel(
                Actions.rotateBy(-90f, .3f),
                Actions.moveTo(775f, 350f, .3f)
            )
        ))

        card1.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.parallel(
                Actions.rotateBy(-90f, .3f),
                Actions.moveTo(1175f, 350f, .3f)
            )
        ))

        UIUtil.resizeProportional(300f, packageOpenedIcon)
        UIUtil.centerBySize(packageOpenedIcon, prefWidth, prefHeight)
        addActor(packageOpenedIcon)
    }

    override fun closeButtonClicked() {

    }
}