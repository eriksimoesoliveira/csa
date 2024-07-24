package com.eriks.core.ui.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image

object ImageCache {

    private val textureCache = HashMap<String, Texture>()

    fun getImage(path: String): Image {
        return Image(textureCache[path] ?: storeTexture(path))
    }

    fun getTexture(path: String): Texture {
        return textureCache[path] ?: storeTexture(path)
    }

    private fun storeTexture(path: String): Texture {
        val image = createTexture(path)
        textureCache[path] = image
        return image
    }

    private fun createTexture(path: String): Texture = Texture(Gdx.files.internal(path))

}