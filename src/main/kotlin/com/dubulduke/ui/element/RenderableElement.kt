package com.dubulduke.ui.element

import com.dubulduke.ui.DynamicUIOptions
import com.dubulduke.ui.event.DynamicEvent
import com.dubulduke.ui.render.RenderDescription
import com.dubulduke.ui.layout.BaseLayout

internal class RenderableElement<T>(private val options: DynamicUIOptions<T>) : Element(options) {
    val children = ArrayList<RenderableElement<T>>()
    private var childCounter = -1

    private val renderer = options.renderer

    private val description = RenderDescription(options, editableStyle, editableLayout)

    override val event = DynamicEvent(description, options.mouseCallback)

    fun setLayout(layout: BaseLayout) {
        editableLayout.copy(layout)
    }

    fun setParentLayout(layout: BaseLayout) {
        parent.copy(layout)
    }

    fun setPreviousLayout(layout: BaseLayout?) {
        if (layout != null) {
            previous.copy(layout)
        } else {
            previous.x = parent.x
            previous.y = parent.y
            previous.width = 0.0
            previous.height = 0.0
        }
    }

    fun render() {
        event.update()
        renderer.render(description)
    }

    override fun addChild() : Element {
        childCounter++
        return if (childExistsAt(childCounter)) {
            children[childCounter].applyParentLayout(editableLayout)
        } else {
            val e = RenderableElement(options)
            children.add(e)
            e.applyParentLayout(editableLayout)
        }
    }

    fun reset() {
        childCounter = -1
        editableLayout.resetPriorities()
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