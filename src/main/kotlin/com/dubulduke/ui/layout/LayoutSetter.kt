package com.dubulduke.ui.layout

interface LayoutSetter {
    fun perform(layout: EditLayout) {
        layout.perform(layout.parent, layout.sibling)
    }

    fun EditLayout.perform(parent: Layout, sibling: Layout)
}

class BasicLayoutSetter(private val setter: EditLayout.(Layout, Layout) -> Unit): LayoutSetter {
    override fun EditLayout.perform(parent: Layout, sibling: Layout) {
        setter(this, parent, sibling)
    }

}