package com.dubulduke.ui.element

import com.dubulduke.ui.DynamicUIOptions
import com.dubulduke.ui.event.DynamicEvent
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.style.Style

abstract class Element(options: DynamicUIOptions<*>) {
    protected val previous = Layout(options)
    protected val parent = Layout(options)

    protected val editableLayout = Layout(options)

    protected val editableStyle = Style()
    
    internal val layout: BaseLayout = editableLayout

    var isFirst: Boolean = true
        protected set

    abstract val event: DynamicEvent

    inline fun layout(setLayout: Layout.(parent: BaseLayout, previous: BaseLayout) -> Unit) {
        setLayout(`access$editableLayout`, `access$parent`, `access$previous`)
    }
    
    inline fun style(setStyle: Style.() -> Unit) {
        setStyle(`access$editableStyle`)
    }

    abstract fun addChild() : Element

    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$editableLayout`: Layout
        get() = editableLayout
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$parent`: Layout
        get() = parent
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$previous`: Layout
        get() = previous
    @Suppress("PropertyName")
    @PublishedApi
    internal val `access$editableStyle`: Style
        get() = editableStyle
}