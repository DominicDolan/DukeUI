package com.dubulduke.ui.element

import com.dubulduke.ui.event.Event
import com.dubulduke.ui.render.RenderDescription

abstract class ElementData<S, E : Event> {
    abstract fun createEvent(description: RenderDescription<S>): E
    abstract fun createStyle(): S
}