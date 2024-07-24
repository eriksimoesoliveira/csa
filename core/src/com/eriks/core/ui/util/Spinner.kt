package com.eriks.core.ui.util

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.ui.UIController

class Spinner(max: Int, clickCallBack: (added: Boolean) -> Unit, initialVal: Int = 0): Group() {

    var value = initialVal

    init {
        setSize(230f, 82f)

        val minus = Label("-", UIController.skin, "Font-64-White")
        minus.width = 80f
        minus.height = 80f
        minus.setAlignment(Align.center)
        UIUtil.centerByHeight(minus, height)
        minus.y += 8f

        val plus = Label("+", UIController.skin, "Font-64-White")
        plus.width = 80f
        plus.height = 80f
        plus.setAlignment(Align.center)
        UIUtil.centerByHeight(plus, height)
        plus.y += 4f
        plus.x = 150f

        val txtField = TextField(value.toString(), UIController.skin, "TF-32").apply {
            width = 70f
            height = 80f
        }
        txtField.alignment = Align.center
        UIUtil.centerByHeight(txtField, height)
        txtField.x = 80f
        txtField.isDisabled = true

        addActor(minus)
        addActor(plus)
        addActor(txtField)

        minus.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val curval = txtField.text.toInt()
                if (curval <= 0) {
                    return
                }
                value = txtField.text.toInt() - 1
                txtField.text = value.toString()
                clickCallBack(false)
            }
        })
        plus.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val curval = txtField.text.toInt()
                if (curval >= max) {
                    return
                }
                value = txtField.text.toInt() + 1
                txtField.text = value.toString()
                clickCallBack(true)
            }
        })

    }
}