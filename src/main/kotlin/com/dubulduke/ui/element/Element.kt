package com.dubulduke.ui.element

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.event.DynamicEvent
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.Dimension
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.Point
import com.dubulduke.ui.render.RenderDescription
import com.dubulduke.ui.style.Style

abstract class Element(val context: UIContext) {
    protected val previous = Layout(context)

    protected val elementLayout = ElementLayout()
    protected val parent = elementLayout.parent

    protected val editableLayout = Layout(context)

    protected val editableStyle = Style()

    protected val description = RenderDescription(context, editableStyle, editableLayout)

    internal val layout: BaseLayout = editableLayout

    var isFirst: Boolean = true
        protected set

    abstract val event: DynamicEvent

    inline fun layout(setLayout: Layout.(parent: BaseLayout, previous: BaseLayout) -> Unit) {
        setLayout(`access$editableLayout`, `access$parent`, `access$previous`)
    }
    
    inline fun style(setStyle: Style.() -> Unit) {
        setStyle(`access$editableStyle`)
    }

    abstract fun addChild(create: (UIContext) -> Element): Element

    protected fun setLayout(layout: BaseLayout) {
        editableLayout.copy(layout)
    }

    protected fun setParentLayout(layout: BaseLayout) {
        elementLayout.parent.copy(layout)
    }

    protected fun setPreviousLayout(layout: BaseLayout?) {
        if (layout != null) {
            previous.copy(layout)
            isFirst = false
        } else {
            previous.x = parent.x
            previous.y = parent.y
            previous.width = 0.0
            previous.height = 0.0
            isFirst = true
        }
    }

    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$editableLayout`: Layout
        get() = editableLayout
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$parent`: BaseLayout
        get() = parent
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$previous`: Layout
        get() = previous
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$editableStyle`: Style
        get() = editableStyle

    inner class ElementLayout : Layout(context) {
        override val parent = RelatedLayout()
    }

    inner class RelatedLayout : BaseLayout {

        private val vertical = Dimension()
        override var x: Double = 0.0
        override var width: Double = 0.0

        override var y: Double = 0.0
        override var height: Double = 0.0


        override val center: Point = object : Point {
            override val x: Double
                get() = this@RelatedLayout.x + width/2.0
            override val y: Double
                get() = this@RelatedLayout.y + height/2.0

        }
        fun copy(other: BaseLayout) {
            this.x = other.x
            this.y = other.y
            this.width = other.width
            this.height = other.height
        }
    }
}