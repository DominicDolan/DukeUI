package com.dubulduke.ui.render

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.element.Element
import com.dubulduke.ui.event.Event

interface ElementRenderer<S, E: Event> {
    fun draw(description: RenderDescription<S>, data: Any?)

    fun createElement(context: UIContext<S, E>): Element<S, E> {
        return Element(context, this)
    }
}