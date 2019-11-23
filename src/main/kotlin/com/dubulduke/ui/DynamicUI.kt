package com.dubulduke.ui

import com.dubulduke.ui.element.Element
import com.dubulduke.ui.layout.EditableLayout
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.setTo

class DynamicUI<T>(viewport: Viewport, private val renderOperation: RenderDescription.(T) -> Unit) {
    private val element = Element()

    init {
        element.editableLayout.setTo(viewport)
        (element.parent as EditableLayout).setTo(viewport)
    }

    operator fun invoke(renderer: T, block: Element.() -> Unit) {
        block(element)

        performLayout(element)
        performStyle(element)

        renderOperation(element.description, renderer)

        render(renderer, element, 0)
    }

    private fun render(renderer: T, element: Element, depth: Int) {
        element.childCounter = -1
        var previous: Layout? = null

        for (child in element.children) {
            child.editableLayout.resetPriorities()
            previous = compute(child, element, previous)
            renderOperation(child.description, renderer)
        }

        for (child in element.children) {
            render(renderer, child, depth + 1)
        }
    }

    private fun compute(child: Element, element: Element, previous: Layout?): Layout {
        setParentLayout(child, element)
        val newPrevious = setPreviousLayout(child, previous)

        computeLayout(child, element)
        performStyle(child)

        return newPrevious
    }

    private fun setParentLayout(child: Element, parent: Element) {
        (child.parent as EditableLayout).setTo(parent.editableLayout)
    }

    private fun setPreviousLayout(child: Element, previous: Layout?): Layout {
        (child.previous as EditableLayout).let {
            if (previous != null) {
                it.setTo(previous)
            } else {
                it.x = child.parent.x
                it.y = child.parent.y
                it.width = 0.0
                it.height = 0.0
            }
        }
        return child.previous
    }

    private fun computeLayout(child: Element, element: Element) {
        child.editableLayout.setTo(element.editableLayout)
        performLayout(child)
    }

    private fun performLayout(element: Element) {
        element.layout(element.editableLayout)
    }

    private fun performStyle(element: Element) {
        element.style(element.editableStyle)
    }
}
