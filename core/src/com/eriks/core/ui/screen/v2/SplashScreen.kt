package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.eriks.core.GameController
import com.eriks.core.be.BackendService
import com.eriks.core.ui.UIController
import com.eriks.core.ui.UIController.changeScreen
import com.eriks.core.ui.screen.v2.dialogs.ServerOfflineDialog
import com.eriks.core.ui.screen.v2.dialogs.WrongVersionDialog
import com.eriks.core.ui.util.ImageCache
import com.eriks.core.ui.util.UIUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen: CSAScreen() {

    private lateinit var waitingLabel: Label
    private var elapsedTime = 0
    private var timerRunning = true

    override fun show() {
        val version = Label(GameController.VERSION, UIController.skin, "BLUE-14")
        version.x = 10f

        val img = ImageCache.getImage("ui/splashscreen.png").apply {
            color.a = 0f
        }
        UIUtil.centerInScreen(img)

        waitingLabel = Label("Waiting for server... 0s", UIController.skin, "Roboto-Bold-28-White").apply {
            x = 10f
            y = 50f
        }

        stage.addActor(img)
        stage.addActor(version)
        stage.addActor(waitingLabel)

        super.show()

        GlobalScope.launch(Dispatchers.IO) {
            val serverResponsive = BackendService.ping()
            Gdx.app.postRunnable {
                img.addAction(SequenceAction(
                    Actions.delay(3f),
                    Actions.run {
                        if (serverResponsive) {
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
                        } else {
                            timerRunning = false
                            ServerOfflineDialog().show(stage)
                        }
                    }
                ))
            }
        }

        img.addAction(SequenceAction(
            Actions.delay(.3f),
            Actions.fadeIn(0.6f),
            Actions.delay(2f)
        ))

        updateWaitingLabel()
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.066f, 0.066f, 0.066f, 0f)
        super.render(delta)
    }

    private fun updateWaitingLabel() {
        GlobalScope.launch(Dispatchers.Main) {
            while (timerRunning) {
                delay(1000L)
                elapsedTime++
                waitingLabel.setText("Waiting for server... ${elapsedTime}s")
            }
        }
    }
}
