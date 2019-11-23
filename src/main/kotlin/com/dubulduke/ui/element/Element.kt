package com.dubulduke.ui.element

import com.dubulduke.ui.RenderDescription
import com.dubulduke.ui.layout.EditableLayout
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.style.EditableStyle

class Element {
    val children = ArrayList<Element>()
    var childCounter = -1

    val previous: Layout = EditableLayout()
    val parent: Layout = EditableLayout()

    internal val editableLayout = EditableLayout()
    internal val editableStyle = EditableStyle()
    internal val description = RenderDescription(editableStyle, editableLayout)

    var layout: EditableLayout.() -> Unit = {}
    var style: EditableStyle.() -> Unit = {}

    private fun childExistsAt(index: Int): Boolean {
        return children.size > index
    }

    internal fun createChild(element: Element): Element {
        element.childCounter++
        return if (element.childExistsAt(element.childCounter)) {
            element.children[element.childCounter]
        } else {
            val e = Element()
            element.children.add(e)
            e
        }
    }
}