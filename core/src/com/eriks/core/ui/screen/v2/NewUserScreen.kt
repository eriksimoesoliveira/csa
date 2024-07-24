package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.eriks.core.GameController
import com.eriks.core.ui.UIController
import com.eriks.core.ui.UIController.changeScreen
import com.eriks.core.ui.util.UIUtil

class NewUserScreen: CSAScreen() {

    private var textField = TextField("", UIController.skin)
    private var labelTitle = Label("Write a GamerTag", UIController.skin)
    private var button = TextButton("OK", UIController.skin)

    init {
        textField.maxLength = 20
        textField.alignment = Align.center

        UIUtil.centerInScreen(labelTitle)
        labelTitle.y = labelTitle.y + 150f

        textField.width = 300f
        UIUtil.centerInScreen(textField)
        textField.y = textField.y +70

        button.width = 150f
        UIUtil.centerInScreen(button)
        button.y = button.y - 10f
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (textField.text != null && textField.text.trim() != "") {
                    GameController.newUserFlow(textField.text.toUpperCase())
                    changeScreen(SplashScreen())
                }
            }
        })

        stage.addActor(labelTitle)
        stage.addActor(textField)
        stage.addActor(button)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.066f, 0.066f, 0.066f, 0f);
        super.render(delta)
    }
}