package com.dubulduke.ui.layout

class Adjustment : BaseLayout {
    override var x: Double = 0.0
    override var y: Double = 0.0
    override var width: Double = 0.0
    override var height: Double = 0.0
    override val center: Point = object : Point {
            override val x: Double
                get() = this@Adjustment.x + width/2.0
            override val y: Double
                get()  = this@Adjustment.y + height/2.0
        }
}