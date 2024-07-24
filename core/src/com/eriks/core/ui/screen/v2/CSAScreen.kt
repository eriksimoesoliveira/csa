package com.eriks.core.ui.screen.v2

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport

open class CSAScreen : Screen {

    protected val stage: Stage
    private val camera: OrthographicCamera
    private val viewport: Viewport

    init {
        camera = OrthographicCamera()
        viewport = FitViewport(1920f, 1080f, camera)
        stage = Stage(viewport)
        Gdx.input.inputProcessor = stage
    }

    override fun show() {
        // Override in subclasses if needed
    }

    override fun render(delta: Float) {
        viewport.apply()
        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f)
        camera.update()
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }
}