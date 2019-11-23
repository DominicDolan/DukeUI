package com.dubulduke.ui.layout

import com.dubulduke.ui.element.Element


fun point(x: Number, y: Number): Point {
    val xBits = java.lang.Float.floatToRawIntBits(x.toFloat())
    val yBits = java.lang.Float.floatToRawIntBits(y.toFloat())
    val xyBits = (yBits.toLong() shl 32 ushr 32) or (xBits.toLong() shl 32)
    return LightweightPoint(xyBits)
}

infix fun EditableLayout.EditablePoint.set(other: Point) {
    this.x = other.x
    this.y = other.y
}

infix fun EditableLayout.EditablePoint.`=`(other: Point) = this set other

fun EditableLayout.setTo(other: Layout) {
    this.x = other.x
    this.y = other.y
    this.width = other.width
    this.height = other.height
}

fun Element.box(block: Element.() -> Unit) {
    val buttonElement = createChild(this)
    block(buttonElement)
}


