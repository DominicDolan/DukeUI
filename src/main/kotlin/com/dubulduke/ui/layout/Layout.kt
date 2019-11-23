package com.dubulduke.ui.layout

interface Layout {
    val x: Double
    val y: Double
    val width: Double
    val height: Double
    val center: Point get() = point(x + width/2.0, y + height/2.0)
    val left: Double get() = x
    val bottom: Double get() = y
    val top: Double get() = y + height
    val right: Double get() = x + width

}