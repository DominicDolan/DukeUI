package com.dubulduke.ui.event

import com.dubulduke.ui.render.RenderDescription

open class Event(protected val description: RenderDescription<*>) {

    open fun update() { }
    open fun load() { }
    open fun destroy() { }

}