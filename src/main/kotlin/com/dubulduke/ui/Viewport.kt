package com.dubulduke.ui

import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.Point

data class Viewport(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double) : BaseLayout {
    override val center: Point = object : Point {
        override val x: Double
            get() = this@Viewport.x + width/2.0
        override val y: Double
            get() = this@Viewport.y + height/2.0

    }
}