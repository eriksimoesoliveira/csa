package com.eriks.core.ui.screen.v2.dialogs

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Dialog
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.ColorCache
import com.eriks.core.ui.util.ImageCache

abstract class FullScreenDialog(private val drawSquare: Boolean, private val putClosedButton: Boolean = true): Dialog("", UIController.skin) {
    private var shapeRenderer = ShapeRenderer()

    private lateinit var closeButton: Image
    private val closeButtonX = 1700f
    private val closeButtonY = 900f

    override fun show(stage: Stage?): Dialog {
        setSize(prefWidth, prefHeight)

        if (putClosedButton) {
            closeButton = ImageCache.getImage("ui/closebutton.png").apply {
                width = 120f
                height = 120f
            }

            closeButton.addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    hide(null)
                    closeButtonClicked()
                }
            })
            closeButton.x = closeButtonX
            closeButton.y = closeButtonY

            addActor(closeButton)
        }
        return super.show(stage)
    }

    open fun closeButtonClicked() {

    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.end()

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.projectionMatrix = batch.projectionMatrix
        shapeRenderer.transformMatrix = batch.transformMatrix

        //Blured background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = ColorCache.getColor("000000DD")
        shapeRenderer.rect(x, y, width, height)
        shapeRenderer.end()

        if (drawSquare) {
            //Filler
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
            shapeRenderer.color = ColorCache.getColor("000000")
            shapeRenderer.rect((width-1500f)/2, (height-844f)/2, 1500f, 844f)
            shapeRenderer.end()

            // Draw the white border with 10px width
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
            shapeRenderer.color = ColorCache.getColor("ababab")
            Gdx.gl.glLineWidth(10f)  // Set the line width to 10 pixels
            shapeRenderer.rect((width-1500f)/2, (height-844f)/2, 1500f, 844f)
            shapeRenderer.end()
        }

        batch.begin()

        super.draw(batch, parentAlpha)
    }

    override fun getPrefWidth(): Float {
        return 1920f
    }

    override fun getPrefHeight(): Float {
        return 1080f
    }

    override fun hide() {
        // Clear all children (actors) from the dialog
        contentTable.clearChildren()
        children.clear()
        super.hide()
    }

    override fun hide(action: Action?) {
        // Clear all children (actors) from the dialog
        contentTable.clearChildren()
        children.clear()
        super.hide(action)
    }
}