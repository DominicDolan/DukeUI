package com.dubulduke.ui.layout

class EditableLayout : Layout {
    private val horizontal = Dimension()
    private val vertical = Dimension()

    override var x: Double
        get() = horizontal.outputPosition
        set(value) {
            horizontal.minimum = value
        }
    override var width: Double
        get() = horizontal.outputSize
        set(value) {
            horizontal.size = value
        }
    override var right: Double
        get() = x + width
        set(value) {
            horizontal.maximum = value
        }
    override var left: Double
        get() = x
        set(value) {
            horizontal.minimum = value
        }

    override var y: Double
        get() = vertical.outputPosition
        set(value) {
            vertical.minimum = value
        }
    override var height: Double
        get() = vertical.outputSize
        set(value) {
            vertical.size = value
        }
    override var top: Double
        get() = y + height
        set(value) {
            vertical.maximum = value
        }
    override var bottom: Double
        get() = y
        set(value) {
            vertical.minimum = value
        }

    override var center: EditablePoint = EditablePoint()
        set(value) {
            horizontal.center = value.x
            vertical.center = value.y
            field = value
        }

    fun resetPriorities() {
        horizontal.resetPriorities()
        vertical.resetPriorities()
    }

    inner class EditablePoint: Point {
        override var x: Double
            get() = this@EditableLayout.x + width/2.0
            set(value) {
                horizontal.center = value
            }
        override var y: Double
            get() = this@EditableLayout.y + height/2.0
            set(value) {
                vertical.center = value
            }
    }

    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}