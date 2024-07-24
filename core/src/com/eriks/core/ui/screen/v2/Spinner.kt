package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.ui.UIController
import com.eriks.core.ui.util.UIUtil

class Spinner(max: Int, clickCallBack: (added: Boolean) -> Unit, initialVal: Int = 0): Group() {

    var value = initialVal

    init {
        setSize(230f, 82f)

//        val bg1 = ImageCache.getImage("scrollpanel-bg").apply {
//            width = 80f
//            height = 80f
//        }
//        UIUtil.centerByHeight(bg1, height)
//        val minus = UIUtil.buildLabel_font100nofill("-")
        val minus = Label("-", UIController.skin, "Roboto-Bold-45")
        minus.width = 80f
        minus.height = 80f
        minus.setAlignment(Align.center)
        UIUtil.centerByHeight(minus, height)
//        minus.y += 8f

//        val bg2 = ImageCache.getImage("scrollpanel-bg").apply {
//            width = 80f
//            height = 80f
//        }
//        bg2.x = 150f
//        UIUtil.centerByHeight(bg2, height)
//        val plus = UIUtil.buildLabel_font100nofill("+")
        val plus = Label("+", UIController.skin, "Roboto-Bold-45")
        plus.width = 80f
        plus.height = 80f
        plus.setAlignment(Align.center)
        UIUtil.centerByHeight(plus, height)
//        plus.y += 4f
        plus.x = 150f

        val txtField = TextField(value.toString(), UIController.skin, "TF-32").apply {
            width = 70f
            height = 60f
        }
        txtField.alignment = Align.center
        UIUtil.centerByHeight(txtField, height)
        txtField.x = 80f
        txtField.isDisabled = true

//        addActor(bg1)
        addActor(minus)
//        addActor(bg2)
        addActor(plus)
        addActor(txtField)

        minus.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                val curval = txtField.text.toInt()
                if (curval <= 1) {
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