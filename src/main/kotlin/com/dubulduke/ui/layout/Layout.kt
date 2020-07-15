package com.dubulduke.ui.layout

interface Layout {
    val x: Double
    val y: Double
    val width: Double
    val height: Double
    val center: Point
    val left: Double
    val bottom: Double
    val top: Double
    val right: Double

    companion object {
        operator fun invoke(setter: EditLayout.(parent: Layout, sibling: Layout) -> Unit) : LayoutSetter {
            return BasicLayoutSetter(setter)
        }

        fun toString(layout: Layout) = "layout: x: ${layout.x}, y: ${layout.y}, width: ${layout.width}, height: ${layout.height}"
    }
}