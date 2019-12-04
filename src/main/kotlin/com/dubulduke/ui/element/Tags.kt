package com.dubulduke.ui.element

inline fun Element.box(block: Element.() -> Unit) {
    val boxElement = addChild()
    block(boxElement)
}
