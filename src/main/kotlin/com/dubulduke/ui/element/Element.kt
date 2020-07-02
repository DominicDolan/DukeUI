package com.dubulduke.ui.element

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.event.Event
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.EditLayout
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.render.ElementRenderer
import com.dubulduke.ui.render.RenderDescription

open class Element<S, E: Event>(
        val context: UIContext<S, E>,
        protected open val renderer: ElementRenderer<S, E>
) {

    val children: ArrayList<Element<S, E>> = ArrayList()
    private var childCounter = -1

    private val elementLayout = ElementLayout()

    val style = context.elementData.createStyle()

    protected val description = RenderDescription(context.window, context.viewport, style, elementLayout)

    val contextUserData: Any?
        get() = context.userData

    val layout: EditLayout = elementLayout

    var isFirst: Boolean = true
        protected set

    val event = context.elementData.createEvent(description)

    inline fun style(setStyle: S.() -> Unit) {
        setStyle(style)
    }

    protected fun setLayout(layout: Layout) {
        elementLayout.copy(layout)
    }

    protected fun setParentLayout(layout: Layout) {
        elementLayout.parent.copy(layout)
    }

    protected fun setPreviousLayout(layout: Layout?) {
        val sibling = elementLayout.sibling
        if (layout != null) {
            sibling.copy(layout)
            isFirst = false
        } else {
            sibling.x = elementLayout.parent.x
            sibling.y = elementLayout.parent.y
            sibling.width = 0.0
            sibling.height = 0.0
            isFirst = true
        }
    }


    protected open fun renderLayout(depth: Int) {
        reset()
        var previous: Layout? = null

        forEachChild { child ->
            child.setPreviousLayout(previous)
            previous = child.layout
        }

        event.update()
        renderer.draw(description, contextUserData)

        forEachChild { child ->
            child.renderLayout(depth + 1)
        }
    }

    private inline fun forEachChild(action: (Element<S,E>) -> Unit) {
        for (i in children.indices) {
            action(children[i])
        }
    }

    inline fun addChildElement(create: (UIContext<S, E>) -> Element<S, E>): Element<S, E> {
        val child = nextChild()
        return if (child == null) {
            val new = create(context)
            addChildElement(new)
            new.event.load()
            new
        } else child
    }

    inline fun addChild(create: () -> ElementRenderer<S, E>): Element<S, E> {
        return addChildElement { context: UIContext<S, E> -> create().createElement(context) }
    }


    fun addChildElement(element: Element<S, E>) {
        if (element.context === this.context) {
            children.add(element)
        } else {
            System.err.println("Element: $element is not being added to the UI hierarchy because it was defined in a different context")
        }
        element.applyParentLayout(elementLayout)
    }

    private fun childExistsAt(index: Int): Boolean {
        return children.size > index
    }

    private fun applyParentLayout(layout: Layout): Element<S, E> {
        this.elementLayout.parent.copy(layout)
        this.elementLayout.copy(layout)
        return this
    }

    @PublishedApi
    internal fun nextChild(): Element<S, E>? {
        childCounter++
        val exists = childExistsAt(childCounter)
        if (exists) {
            return children[childCounter].applyParentLayout(elementLayout)
        }
        return null
    }

    inline fun <reified T> useUserData(use: (T) -> Unit) {
        val data = contextUserData
        if (data != null && data is T) {
            use(data)
        }
    }

    fun reset() {
        childCounter = -1
        elementLayout.resetPriorities()
    }


    protected inner class ElementLayout : EditLayout(context) {
        override val parent = RelatedLayout()
        override val sibling = RelatedLayout()
    }

    protected inner class RelatedLayout : BaseLayout(context) {

        override var x: Double = 0.0
        override var y: Double = 0.0
        override var width: Double = 0.0
        override var height: Double = 0.0

        fun copy(other: Layout) {
            this.x = other.x
            this.y = other.y
            this.width = other.width
            this.height = other.height
        }
    }
}