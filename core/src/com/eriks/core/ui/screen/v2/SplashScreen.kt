package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.eriks.core.GameController
import com.eriks.core.ui.UIController
import com.eriks.core.ui.UIController.changeScreen
import com.eriks.core.ui.screen.v2.dialogs.WrongVersionDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil

class SplashScreen: CSAScreen() {

    override fun show() {
        GameController.versionCheck()

        val version = Label(GameController.VERSION, UIController.skin, "BLUE-14")
        version.x = 10f

        val img = ImageCache.getImage("ui/splashscreen.png").apply {
            color.a = 0f
        }
        UIUtil.centerInScreen(img)

        stage.addActor(img)
        stage.addActor(version)

        super.show()

        img.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.fadeIn(0.6f),
            Actions.delay(2f),
            Actions.run {
                if (!GameController.isVersionValid) {
                    WrongVersionDialog().show(stage)
                } else {
                    if (GameController.isNewUser()) {
                        changeScreen(NewUserScreen())
                    } else {
                        GameController.login()
                        changeScreen(MainScreen())
                    }
                }
            }
        ))
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.066f, 0.066f, 0.066f, 0f)
        super.render(delta)
    }
}
