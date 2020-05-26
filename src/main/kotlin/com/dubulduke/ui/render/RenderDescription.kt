package com.dubulduke.ui.render

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.style.Style
import com.dubulduke.ui.style.BaseStyle

class RenderDescription(
        context: UIContext,
        private val style: Style,
        private val layout: Layout)
    : BaseStyle by style, BaseLayout by layout {

    private val widthScale = context.window.width/context.viewport.width
    private val heightScale = context.window.height/context.viewport.height

    private val signAdjustX = if(widthScale < 0.0) { context.window.width } else 0.0
    private val signAdjustY = if(heightScale < 0.0) { context.window.height } else 0.0

    private val xAdjust = context.window.x - widthScale*context.viewport.x + signAdjustX
    private val yAdjust = context.window.y - heightScale*context.viewport.y + signAdjustY

    override val x: Double
        get() = (layout.x + style.adjustment.x)*widthScale + xAdjust

    override val y: Double
        get() = (layout.y + style.adjustment.y)*heightScale + yAdjust

    override val width: Double
        get() = (layout.width + style.adjustment.width)*widthScale

    override val height: Double
        get() = (layout.height + style.adjustment.height)*heightScale

    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}