package com.dubulduke.ui.layout

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.dimension.VaryingDimension

abstract class EditLayout(context: UIContext<*,*>) : Layout {
    private val horizontal: VaryingDimension = VaryingDimension(context.leftIsLow)
    private val vertical: VaryingDimension = VaryingDimension(context.bottomIsLow)

    abstract val parent: Layout
    abstract val sibling: Layout

    val visualAdjustment = Adjustment(context)

    override var x: Double
        get() = horizontal.origin
        set(value) { horizontal.origin = value }
    override var width: Double
        get() = horizontal.size
        set(value) { horizontal.size = value }
    override var right: Double
        get() = horizontal.top
        set(value) { horizontal.top = value }
    override var left: Double
        get() = horizontal.bottom
        set(value) { horizontal.bottom = value }

    override var y: Double
        get() = vertical.origin
        set(value) { vertical.origin = value }
    override var height: Double
        get() = vertical.size
        set(value) { vertical.size = value }
    override var top: Double
        get() = vertical.top
        set(value) { vertical.top = value }
    override var bottom: Double
        get() = vertical.bottom
        set(value) { vertical.bottom = value }

    override val center: Center = Center()

    abstract val isFirst: Boolean

    inline fun edit(setLayout: EditLayout.(parent: Layout, sibling: Layout) -> Unit) {
        setLayout(this, parent, sibling)
    }

    fun add(layoutSetter1: LayoutSetter, layoutSetter2: LayoutSetter, layoutSetter3: LayoutSetter, layoutSetter4: LayoutSetter) {
        add(layoutSetter1, layoutSetter2, layoutSetter3)
        layoutSetter4.perform(this)
    }
    fun add(layoutSetter1: LayoutSetter, layoutSetter2: LayoutSetter, layoutSetter3: LayoutSetter) {
        add(layoutSetter1, layoutSetter2)
        layoutSetter3.perform(this)
    }
    fun add(layoutSetter1: LayoutSetter, layoutSetter2: LayoutSetter) {
        add(layoutSetter1)
        layoutSetter2.perform(this)
    }
    fun add(layoutSetter: LayoutSetter) = layoutSetter.perform(this)

    inline fun visualAdjustment(setVisual: Adjustment.() -> Unit) {
        setVisual(visualAdjustment)
    }

    internal fun resetPriorities() {
        horizontal.resetPriorities()
        vertical.resetPriorities()
    }

    internal fun copy(other: Layout) {
        this.x = other.x
        this.y = other.y
        this.width = other.width
        this.height = other.height
    }

    inner class Center: Point {
        override var x: Double
            get() = horizontal.center
            set(value) {
                horizontal.center = value
            }
        override var y: Double
            get() = vertical.center
            set(value) {
                vertical.center = value
            }

        fun set(other: Point) {
            horizontal.center = other.x
            vertical.center = other.y
        }
    }

    override fun toString() = Layout.toString(this)
}