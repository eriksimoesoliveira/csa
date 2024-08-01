package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.GameController
import com.eriks.core.objects.Card
import com.eriks.core.ui.screen.v2.CardGroup
import com.eriks.core.ui.util.FullScreenDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil
import java.util.*

class HandCardsDialog(val closeDialogCallback: () -> Unit): FullScreenDialog(false) {

    private lateinit var handCards: List<Card>
    private lateinit var currentCard: CardGroup
    private lateinit var nextCard: CardGroup
    private val centerScreenDestinyX = 210f
    private val centerScreenDestinyY = 118f

    private var glueImageButton = ImageCache.getImage("ui/glueicon.png")
    private var sellImageButton = ImageCache.getImage("ui/dollarbutton.png")

    override fun show(stage: Stage?): Dialog {
        handCards = GameController.handCards

        currentCard = CardGroup(handCards.first(), true, false, GameController.getCardInfo(handCards.first()), true)
        addActor(currentCard)
        UIUtil.centerInScreen(currentCard)

        val arrowRight = ImageCache.getImage("ui/rightbutton.png").apply {
            width = 150f
            height = 150f
        }
        arrowRight.x = 1730f
        arrowRight.y = 150f
        addActor(arrowRight)
        arrowRight.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                moveRight()
            }
        })

        val arrowLeft = ImageCache.getImage("ui/rightbutton.png").apply {
            width = 150f
            height = 150f
        }
        arrowLeft.rotateBy(180f)
        arrowLeft.x = 200f
        arrowLeft.y = 300f
        addActor(arrowLeft)
        arrowLeft.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                moveLeft()
            }
        })

        refreshButtons(handCards.first())

        return super.show(stage)
    }

    override fun closeButtonClicked() {
        closeDialogCallback()
    }

    private fun moveRight() {
        //Rotate the list of handCards
        Collections.rotate(handCards, 1)

        //Create new instance at LEFT
        nextCard = CardGroup(handCards.first(), true, false, GameController.getCardInfo(handCards.first()), true)
        //Add it into the scene
        nextCard.y = currentCard.y
        addActor(nextCard)

        val oldCard = currentCard

        //Move both instance to the right
        oldCard.addAction(SequenceAction(
            Actions.moveTo(prefWidth, centerScreenDestinyY, .2f),
            Actions.run { oldCard .remove() }
        ))
        nextCard.addAction(
            SequenceAction(
            Actions.moveTo(centerScreenDestinyX, centerScreenDestinyY, .2f)
        ))

        currentCard = nextCard

        refreshButtons(handCards.first())
    }

    private fun moveLeft() {
        //Rotate the list of handCards
        Collections.rotate(handCards, -1)

        //Create new instance at LEFT
        nextCard = CardGroup(handCards.first(), true, false, GameController.getCardInfo(handCards.first()), true)
        //Add it into the scene
        nextCard.y = currentCard.y
        addActor(nextCard)

        val oldCard = currentCard

        //Move both instance to the right
        oldCard.addAction(SequenceAction(
            Actions.moveTo(-1500f, centerScreenDestinyY, .2f),
            Actions.run { oldCard.remove() }
        ))
        nextCard.addAction(
            SequenceAction(
            Actions.moveTo(centerScreenDestinyX, centerScreenDestinyY, .2f)
        ))

        currentCard = nextCard

        refreshButtons(handCards.first())
    }

    private fun refreshButtons(card: Card) {
        val canGlueCard = GameController.canGlueCard(card)

        removeActor(glueImageButton)
        removeActor(sellImageButton)

        glueImageButton = if (handCards.isNotEmpty() && GameController.canGlueCard(handCards.first())) {
            ImageCache.getImage("ui/glueicon.png")
        } else {
            ImageCache.getImage("ui/glueicon-off.png")
        }

        glueImageButton.width = 150f
        glueImageButton.height = 150f
        glueImageButton.color.a = 0f
        glueImageButton.x = 800f
        glueImageButton.y = 30f
        addActor(glueImageButton)
        glueImageButton.addAction(SequenceAction(
            Actions.fadeIn(0.3f),
        ))
        glueImageButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                //Checa se pode ser colado
                if (canGlueCard) {
                    //GameController cola
                    GameController.glueCard(card)
                    //Fecha o dialog
                    //hide(null)  //REMOVED for QUALITY OF LIFE IMPROVEMENT
                    //Chama o MainScreen e seta a board e viewpoint de onde esta a figurinha
                    //Faz um hightlight
//                    colaCardCallBack(card)

                    handCards = GameController.handCards
                    if (handCards.isEmpty()) {
                        hide(null)
                    } else {
                        moveLeft()
                    }
                }
            }
        })

        sellImageButton = if (!GameController.canGlueCard(card)) {
            ImageCache.getImage("ui/dollarbutton.png")
        } else {
            ImageCache.getImage("ui/dollarbutton-off.png")
        }
        sellImageButton.width = 150f
        sellImageButton.height = 150f
        sellImageButton.color.a = 0f
        sellImageButton.x = 1000f
        sellImageButton.y = 30f
        addActor(sellImageButton)
        sellImageButton.addAction(
            SequenceAction(
                Actions.fadeIn(0.3f),
            )
        )
        if (!GameController.canGlueCard(card)) {
            sellImageButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    GameController.sellCard(card)
                    handCards = GameController.handCards
                    if (handCards.isEmpty()) {
                        hide(null)
                    } else {
                        moveLeft()
                    }
                }
            })
        }



    }

}