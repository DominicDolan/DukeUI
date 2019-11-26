package com.dubulduke.ui

import com.dubulduke.ui.element.EditableElement
import com.dubulduke.ui.element.Element
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.Point
import kotlin.math.abs

class DynamicUI<T>(private val options: DynamicUIOptions<T>) {
    private var element: EditableElement<T>? = null

    private fun getElementInstance(renderer: T): EditableElement<T> {
        return this.element ?: {
            options.setRenderer(renderer)
            val e = EditableElement(options)
            this.element = e
            e.setLayout(Viewport(
                    options.viewport.x,
                    options.viewport.y,
                    abs(options.viewport.width),
                    abs(options.viewport.height)
                    )
            )
            e.setParentLayout(options.viewport)
            e
        }.invoke()
    }

    operator fun invoke(renderer: T, block: Element.() -> Unit) {
        val element = getElementInstance(renderer)

        block(element)

        element.render()

        recurThroughHierarchy(renderer, element, 0)
    }

    private fun recurThroughHierarchy(renderer: T, element: EditableElement<T>, depth: Int) {
        element.reset()
        var previous: BaseLayout? = null

        for (child in element.children) {
            child.setPreviousLayout(previous)
            previous = child.layout
            child.render()
        }

        for (child in element.children) {
            recurThroughHierarchy(renderer, child, depth + 1)
        }
    }
}
