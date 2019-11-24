package com.dubulduke.ui

import com.dubulduke.ui.element.EditableElement
import com.dubulduke.ui.element.Element
import com.dubulduke.ui.layout.BaseLayout

class DynamicUI<T>(private val viewport: Viewport, private val renderOperation: RenderDescription.(T) -> Unit) {
    private var element: EditableElement<T>? = null

    private fun getElementInstance(renderer: T): EditableElement<T> {
        val element = this.element ?: EditableElement(renderer)
        this.element = element
        element.setLayout(viewport)
        element.setParentLayout(viewport)
        return element
    }

    operator fun invoke(renderer: T, block: Element.() -> Unit) {
        val element = getElementInstance(renderer)

        block(element)

        element.render(renderOperation)

        recurHierarchy(renderer, element, 0)
    }

    private fun recurHierarchy(renderer: T, element: EditableElement<T>, depth: Int) {
        element.reset()
        var previous: BaseLayout? = null

        for (child in element.children) {
            child.setPreviousLayout(previous)
            previous = child.layout
            child.render(renderOperation)
        }

        for (child in element.children) {
            recurHierarchy(renderer, child, depth + 1)
        }
    }
}
