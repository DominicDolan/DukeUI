package com.dubulduke.ui

import com.dubulduke.ui.element.RenderableElement
import com.dubulduke.ui.element.Element
import com.dubulduke.ui.layout.BaseLayout
import kotlin.math.abs

class DynamicUI(private val context: UIContext) {
    private var element: RenderableElement? = null

    private fun getFirstElementInstance(): RenderableElement {
        return this.element ?: {
            val e = context.createBaseElement()
            this.element = e
            e.setLayout(Viewport(
                    context.viewport.x,
                    context.viewport.y,
                    abs(context.viewport.width),
                    abs(context.viewport.height)
                    )
            )
            e.setParentLayout(context.viewport)
            e
        }.invoke()
    }

    operator fun invoke(userData: Any? = null, block: Element.() -> Unit) {
        context.setUserData(userData)
        val element = getFirstElementInstance()

        block(element)

        element.render()

        recurThroughHierarchy(element, 0)
    }

    private fun recurThroughHierarchy(element: RenderableElement, depth: Int) {
        element.reset()
        var previous: BaseLayout? = null

        for (child in element.children) {
            child.setPreviousLayout(previous)
            previous = child.layout
            child.render()
        }

        for (child in element.children) {
            recurThroughHierarchy(child, depth + 1)
        }
    }
}
