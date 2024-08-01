package com.eriks.core.ui.screen.v2.board

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.eriks.core.objects.Family
import com.eriks.core.ui.util.ImageCache

abstract class BoardGroup(private val family: Family): Group() {

    var showConditions = false

    init {
        setSize(1500f, 1080f)

        val background = ImageCache.getImage(family.bgName)
        addActor(background)

        buildCards(family)

        addListener(object : InputListener() {
            override fun enter(event: InputEvent?, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                showConditions = true
                refreshCards()
            }

            override fun exit(event: InputEvent?, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                showConditions = false
                refreshCards()
            }
        })

    }

    abstract fun buildCards(family: Family)

    abstract fun refreshCards()
}