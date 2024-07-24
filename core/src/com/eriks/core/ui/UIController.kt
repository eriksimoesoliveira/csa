package com.eriks.core.ui

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.eriks.core.GameController
import com.eriks.core.ui.screen.v2.NewUserScreen
import com.eriks.core.ui.screen.v2.SplashScreen
import com.eriks.core.ui.util.FreeTypeFontGambi

object UIController: Game() {

    var batch: SpriteBatch? = null
    lateinit var skin: Skin

    override fun create() {
        batch = SpriteBatch()

        skin = FreeTypeFontGambi.loadSkin("skin/skin.json")

        if (GameController.isNewUser()) {
            changeScreen(NewUserScreen())
        } else {
            changeScreen(SplashScreen())
        }
    }

    override fun dispose() {
        batch!!.dispose()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        Gdx.gl.glClearColor(0.255f, 0.066f, 0.066f, 0f);
        super.render()
    }

    fun changeScreen(screenToGo : Screen) {
        setScreen(screenToGo)
    }
}