package com.dubulduke.ui.layout

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.dimension.Dimension

abstract class BaseLayout(context: UIContext<*,*>) : Layout {

    private val horizontal = object : Dimension(context.leftIsLow) {
        override val origin: Double get() = x
        override val size: Double get() = width
    }
    private val vertical = object : Dimension(context.bottomIsLow) {
        override val origin: Double get() = y
        override val size: Double get() = height
    }

    override val center: Point = object : Point {
        override val x: Double
            get() = horizontal.center
        override val y: Double
            get() = vertical.center

    }

    override val left: Double
        get() = horizontal.bottom
    override val right: Double
        get() = horizontal.top
    override val bottom: Double
        get() = vertical.bottom
    override val top: Double
        get() = vertical.top

}