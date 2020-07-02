package com.dubulduke.ui.render

import com.dubulduke.ui.Viewport
import com.dubulduke.ui.Window
import com.dubulduke.ui.layout.EditLayout
import com.dubulduke.ui.layout.Layout
import kotlin.math.max
import kotlin.math.min

class RenderDescription<S>(
        window: Window, viewport: Viewport,
        val style: S,
        private val layout: EditLayout)
    : Layout by layout {

    private val widthScale = window.width/viewport.width
    private val heightScale = window.height/viewport.height

    private val signAdjustX = if(widthScale < 0.0) { window.width } else 0.0
    private val signAdjustY = if(heightScale < 0.0) { window.height } else 0.0

    private val xAdjust = window.x - widthScale*viewport.x + signAdjustX
    private val yAdjust = window.y - heightScale*viewport.y + signAdjustY

    override val x: Double
        get() = (layout.x + layout.visualAdjustment.x)*widthScale + xAdjust

    override val y: Double
        get() = (layout.y + layout.visualAdjustment.y)*heightScale + yAdjust

    override val width: Double
        get() = (layout.width + layout.visualAdjustment.width)*widthScale

    override val height: Double
        get() = (layout.height + layout.visualAdjustment.height)*heightScale

    fun contains(x: Double, y: Double): Boolean {
        val p1x = min(this.x, this.x + this.width)
        val p2x = max(this.x, this.x + this.width)

        val p1y = min(this.y, this.y + this.height)
        val p2y = max(this.y, this.y + this.height)

        return x > p1x && x < p2x && y > p1y && y < p2y
    }
    
    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}