package com.dubulduke.ui.element

import com.dubulduke.ui.DynamicUIOptions
import com.dubulduke.ui.RenderDescription
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.Point
import com.dubulduke.ui.style.Style

internal class EditableElement<T>(private val options: DynamicUIOptions<T>) : Element {
    val children = ArrayList<EditableElement<T>>()
    var childCounter = -1

    private val previous = Layout()
    private val parent = Layout()

    private val editableLayout = Layout()
    val layout: BaseLayout = editableLayout
//    private val outputLayout

    private val editableStyle = Style()
    private val description = RenderDescription(options, editableStyle, editableLayout)

    override fun layout(setLayout: Layout.(parent: BaseLayout, previous: BaseLayout) -> Unit) {
        setLayout(editableLayout, parent, previous)
    }

    override fun style(setStyle: Style.() -> Unit) {
        setStyle(editableStyle)
    }

    override fun addChild(): Element {
        childCounter++
        return if (childExistsAt(childCounter)) {
            children[childCounter].applyParentLayout(editableLayout)
        } else {
            val e = EditableElement(options)
            children.add(e)
            e.applyParentLayout(editableLayout)
        }
    }

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
        options.renderOperation(description, options.renderer
                ?: throw IllegalStateException("A rendering object has not been passed down the UI hierarchy, Unable to render element"))
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

    private inner class OutputLayout : BaseLayout {
        override val x: Double
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        override val y: Double
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        override val width: Double
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        override val height: Double
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        override val center: Point
            get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    }
}