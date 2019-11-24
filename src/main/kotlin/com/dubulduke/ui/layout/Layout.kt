package com.dubulduke.ui.layout

class Layout : BaseLayout {
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

    override val center: EditablePoint = EditablePoint()

    internal fun resetPriorities() {
        horizontal.resetPriorities()
        vertical.resetPriorities()
    }

    internal fun copy(other: BaseLayout) {
        this.x = other.x
        this.y = other.y
        this.width = other.width
        this.height = other.height
    }

    inner class EditablePoint: Point {
        override var x: Double
            get() = this@Layout.x + width/2.0
            set(value) {
                horizontal.center = value
            }
        override var y: Double
            get() = this@Layout.y + height/2.0
            set(value) {
                vertical.center = value
            }

        fun set(other: Point) {
            horizontal.center = other.x
            vertical.center = other.y
        }
    }

    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}