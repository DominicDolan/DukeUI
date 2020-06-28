package com.dubulduke.ui.element

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.event.DynamicEvent
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.render.RenderDescription

abstract class RenderableElement(context: UIContext) : Element(context) {
    val children = ArrayList<RenderableElement>()
    private var childCounter = -1

    override val event = DynamicEvent(description, context.mouseCallback)

    val contextUserData: Any?
        get() = context.userData

    fun renderLayout(depth: Int) {
        reset()
        var previous: BaseLayout? = null

        for (child in children) {
            child.setPreviousLayout(previous)
            previous = child.layout
            child.event.update()
            child.draw()
        }

        for (child in children) {
            child.renderLayout(depth + 1)
        }
    }

    private fun draw() = draw(description)

    abstract fun draw(description: RenderDescription)

    inline fun <reified T> useUserData(use: (T) -> Unit) {
        val data = contextUserData
        if (data != null && data is T) {
            use(data)
        }
    }

    override fun addChild(create: (UIContext) -> Element): Element {
        val child = incrementChild()
        return if (child == null) {
            val new = create(context)
            if (new is RenderableElement) addNewChild(new) else throw UnsupportedOperationException("Unable to add this type of element to the tree, the element should extend RenderableElement")
            new
        } else child
    }


    private fun addNewChild(element: RenderableElement) {
        if (element.context === this.context) {
//            @Suppress("UNCHECKED_CAST") // This cast will work if the two context objects are the same instance
            children.add(element)
        } else {
            System.err.println("Element: $element is not being added to the UI hierarchy because it was defined in a different context")
        }
        element.applyParentLayout(editableLayout)
    }

    fun reset() {
        childCounter = -1
        editableLayout.resetPriorities()
    }

    private fun incrementChild(): Element? {
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