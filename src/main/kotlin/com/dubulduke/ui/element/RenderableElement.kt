package com.dubulduke.ui.element

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.event.DynamicEvent
import com.dubulduke.ui.render.RenderDescription
import com.dubulduke.ui.layout.BaseLayout

abstract class RenderableElement(context: UIContext) : Element(context) {
    val children = ArrayList<RenderableElement>()
    private var childCounter = -1

    private val description = RenderDescription(context, editableStyle, editableLayout)

    override val event = DynamicEvent(description, context.mouseCallback)

    val contextUserData: Any?
        get() = context.userData

    fun setLayout(layout: BaseLayout) {
        editableLayout.copy(layout)
    }

    fun setParentLayout(layout: BaseLayout) {
        parent.copy(layout)
    }

    fun setPreviousLayout(layout: BaseLayout?) {
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

    fun render() {
        event.update()
        draw(description)
    }

    abstract fun draw(description: RenderDescription)

    inline fun <reified T> useUserData(use: (T) -> Unit) {
        val data = contextUserData
        if (data != null && data is T) {
            use(data)
        }
    }

    inline fun addChild(create: (UIContext) -> RenderableElement): Element {
        val child = incrementChild()
        return if (child == null) {
            val new = create(context)
            addNewChild(new)
            new
        } else child
    }


    fun addNewChild(element: RenderableElement) {
        if (element.context === this.context) {
            @Suppress("UNCHECKED_CAST") // This cast will work if the two options objects are the same instance
            children.add(element)
        } else {
            System.err.println("Element is not being added to the UI because it was defined in a different context")
        }
        element.applyParentLayout(editableLayout)
    }

    fun reset() {
        childCounter = -1
        editableLayout.resetPriorities()
    }

    fun incrementChild(): Element? {
        childCounter++
        val exists = childExistsAt(childCounter)
        if (exists) {
            return children[childCounter].applyParentLayout(editableLayout)
        }
        return null
    }

    private fun childExistsAt(index: Int): Boolean {
        return children.size > index
    }

    private fun applyParentLayout(layout: BaseLayout): Element {
        this.parent.copy(layout)
        this.editableLayout.copy(layout)
        return this
    }
}