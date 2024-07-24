package com.eriks.core.ui.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Widget
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import java.text.NumberFormat


object UIUtil {

    val decimalFormat = NumberFormat.getCurrencyInstance()

    fun getColoredDrawable(width: Int, height: Int, color: Color?): Drawable? {
        println("PixMap created!")
        val pixmap = Pixmap(width, height, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fill()
        val drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()
        return drawable
    }

    fun getColoredCircle(diameter: Int, color: Color?): Drawable? {
        val pixmap = Pixmap(diameter, diameter, Pixmap.Format.RGBA8888)
        pixmap.setColor(color)
        pixmap.fillCircle((diameter/2), (diameter/2), (diameter/2))
        val drawable = TextureRegionDrawable(TextureRegion(Texture(pixmap)))
        pixmap.dispose()
        return drawable
    }

    fun centerInScreen(widget: Widget) {
        widget.setPosition(
            1920f /2 - widget.width /2,
            1080f /2 - widget.height /2)
    }

    fun centerInScreen(actor: Actor) {
        actor.setPosition(
            1920f /2 - actor.width /2,
            1080f /2 - actor.height /2)
    }

    fun align_centerInParent(actor: Actor) {
        actor.setPosition(
            actor.parent.width /2 - actor.width /2,
            actor.parent.height /2 - actor.height /2)
    }

    fun align_centerTopInParent(actor: Actor, padTop: Float = 0f) {
        actor.setPosition(
            actor.parent.width /2 - actor.width /2,
            actor.parent.height - actor.height - padTop)
    }

    fun align_leftTopInParent(actor: Actor, padTop: Float = 0f, padLeft: Float = 0f) {
        actor.setPosition(
            0f + padLeft,
            actor.parent.height - actor.height - padTop)
    }

    fun align_leftBottomInParent(actor: Actor, padBottom: Float = 0f, padLeft: Float = 0f) {
        actor.setPosition(
            0f + padLeft,
            0f + padBottom)
    }

    fun align_centerBottomInParent(actor: Actor, padBottom: Float = 0f) {
        actor.setPosition(
            actor.parent.width /2 - actor.width /2,
            0 + padBottom)
    }

    fun align_bottomRightInParent(actor: Actor, padBottom: Float = 0f, padRight: Float = 0f) {
        actor.setPosition(
            actor.parent.width - actor.width - padRight,
            0 + padBottom)
    }

    fun align_topRightInParent(actor: Actor, padTop: Float = 0f, padRight: Float = 0f) {
        actor.setPosition(
            actor.parent.width - actor.width - padRight,
            actor.parent.height - actor.height - padTop)
    }

    fun centerBySize(actor: Actor, width: Float, height: Float) {
        actor.setPosition(
            width /2 - actor.width /2,
            height /2 - actor.height /2)
    }

    fun centerByWidth(actor: Actor, width: Float) {
        actor.x = width /2 - actor.width /2
    }

    fun centerByHeight(actor: Actor, height: Float) {
        actor.y = height /2 - actor.height /2
    }

    fun resizeProportional(width: Float, widget: Widget) = widget.setSize(width, (width*widget.height)/widget.width)

    fun resizeProportional(width: Float, actor: Actor) = actor.setSize(width, (width*actor.height)/actor.width)

    fun resizeProportional(widget: Widget, height: Float) = widget.setSize(height*widget.width/widget.height, height)


    fun flip(widget: Widget) {
        widget.width = widget.width * -1
        widget.x = widget.x + -widget.width
    }



}