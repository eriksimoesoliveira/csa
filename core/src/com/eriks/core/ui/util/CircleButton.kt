package com.eriks.core.ui.util

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.eriks.core.ui.UIController

class CircleButton(image: String, ww: Float, hh: Float, var valueToShow: Int?, clickCallBack: () -> Unit): Group() {

    private val valueToShowLabel = Label(valueToShow.toString(), UIController.skin, "Roboto-Bold-45")

    init {
        setSize(ww, hh)
        addActor(
            ImageCache.getImage(image).apply {
                width = ww
                height = hh
            }
        )

        addActor(valueToShowLabel)

        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                clickCallBack()
            }
        })
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (valueToShow != null) {
            valueToShowLabel.isVisible = true
            valueToShowLabel.setText(valueToShow.toString())
        } else {
            valueToShowLabel.isVisible = false
        }
        super.draw(batch, parentAlpha)
    }

}