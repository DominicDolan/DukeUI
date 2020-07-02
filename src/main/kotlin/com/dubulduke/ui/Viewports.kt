package com.dubulduke.ui

class Viewport(
        val x: Double,
        val y: Double,
        val width: Double,
        val height: Double)

open class Window(
        val x: Double,
        val y: Double,
        val width: Double,
        val height: Double) {
    open val bottom: Double
            get() = y
    open val top: Double
            get() = y + height
    open val left: Double
            get() = x
    open val right: Double
            get() = x + width

}