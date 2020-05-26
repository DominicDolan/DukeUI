package com.dubulduke.ui.layout

import com.dubulduke.ui.UIContext

class Layout(private val context: UIContext) : BaseLayout {
    private val horizontal = Dimension()
    private val vertical = Dimension()

    private val xOriginIsLeft: Boolean
    private val yOriginIsAtBottom: Boolean

    override var x: Double
        get() = horizontal.calculatedOrigin
        set(value) {
            horizontal.origin = value
        }
    override var width: Double
        get() = horizontal.calculatedSize
        set(value) {
            horizontal.size = value
        }
    override var right: Double
        get() = if (xOriginIsLeft) horizontal.calculatedOrigin + horizontal.calculatedSize
                else horizontal.calculatedOrigin
        set(value) {
            if (xOriginIsLeft) {
                horizontal.end = value
            } else {
                horizontal.origin = value
            }
        }
    override var left: Double
        get() = if (xOriginIsLeft) horizontal.calculatedOrigin
                else horizontal.calculatedOrigin + horizontal.calculatedSize
        set(value) {
            if (xOriginIsLeft) {
                horizontal.origin = value
            } else {
                horizontal.end = value
            }
        }

    override var y: Double
        get() = vertical.calculatedOrigin
        set(value) {
            vertical.origin = value
        }
    override var height: Double
        get() = vertical.calculatedSize
        set(value) {
            vertical.size = value
        }
    override var top: Double
        get() = if (yOriginIsAtBottom) vertical.calculatedOrigin + vertical.calculatedSize
                else vertical.calculatedOrigin
        set(value) {
            if (yOriginIsAtBottom) {
                vertical.end = value
            } else {
                vertical.origin = value
            }
        }
    override var bottom: Double
        get() = if (yOriginIsAtBottom) vertical.calculatedOrigin
                else vertical.calculatedOrigin + vertical.calculatedSize
        set(value) {
            if (yOriginIsAtBottom) {
                vertical.origin = value
            } else {
                vertical.end = value
            }
        }

    override val center: EditablePoint = EditablePoint()

    init {
        val horizontalIsRightwards = context.xDirection == UIContext.XDirection.RIGHT
        val viewportWidthIsPositive = context.viewport.width >= 0
        xOriginIsLeft = !(horizontalIsRightwards xor viewportWidthIsPositive)

        val verticalIsUpwards = context.yDirection == UIContext.YDirection.UP
        val viewportHeightIsPositive = context.viewport.height >= 0
        yOriginIsAtBottom = !(verticalIsUpwards xor viewportHeightIsPositive)
    }

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

    private fun calculateTopFromOptions(): Double {
        if (context.yDirection == UIContext.YDirection.DOWN) {
            if (context.viewport.height < 0) {
                return 0.0
            } else {
                return 0.0
            }
        } else {
            if (context.viewport.height < 0) {
                return 0.0
            } else {
                return 0.0
            }
        }
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