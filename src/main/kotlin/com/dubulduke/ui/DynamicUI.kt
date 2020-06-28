package com.dubulduke.ui

import com.dubulduke.ui.element.Element
import com.dubulduke.ui.element.RenderableElement

class DynamicUI(private val context: UIContext) {
    private val element: RenderableElement by lazy { context.createBaseElement() }

    operator fun invoke(userData: Any? = null, block: Element.() -> Unit) {
        context.setUserData(userData)
        block(element)

//        element.render()

        element.renderLayout(0)
    }

}
