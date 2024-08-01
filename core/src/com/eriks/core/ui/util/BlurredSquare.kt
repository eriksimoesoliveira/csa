package com.eriks.core.ui.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor

class BlurredSquare(val xx: Float, val yy: Float, val ww: Float, val hh: Float): Actor() {

    private var shapeRenderer = ShapeRenderer()

    init {
//        debug()
        setBounds(xx, yy, ww, hh)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.end()

        Gdx.gl.glEnable(GL20.GL_BLEND)
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)

        shapeRenderer.projectionMatrix = batch.projectionMatrix
        shapeRenderer.transformMatrix = batch.transformMatrix

        //Blured background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = ColorCache.getColor("000000EA")
        shapeRenderer.rect(xx, yy, ww, hh)
        shapeRenderer.end()

        batch.begin()

        super.draw(batch, parentAlpha)
    }

}