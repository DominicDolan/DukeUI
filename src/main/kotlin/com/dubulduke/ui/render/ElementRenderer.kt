package com.dubulduke.ui.render

import com.dubulduke.ui.UIContext
import com.dubulduke.ui.element.Element
import com.dubulduke.ui.event.Event

interface ElementRenderer<S, E: Event, T: Element<S, E>> {
    fun draw(description: RenderDescription<S>, data: Any?)

    fun createElement(context: UIContext<S, E>): T
}

interface DefaultElementRenderer<S, E: Event> : ElementRenderer<S, E, Element<S, E>> {
    override fun createElement(context: UIContext<S, E>): Element<S, E> {
        return Element(context, this)
    }
}