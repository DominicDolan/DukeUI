package com.dubulduke.ui.element

import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.style.Style

interface Element {
    fun layout(setLayout: Layout.(parent: BaseLayout, previous: BaseLayout) -> Unit)
    fun style(setStyle: Style.() -> Unit)
    fun addChild() : Element
}