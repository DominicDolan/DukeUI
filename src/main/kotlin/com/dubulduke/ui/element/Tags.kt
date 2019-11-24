package com.dubulduke.ui.element

fun Element.box(block: Element.() -> Unit) {
    val boxElement = addChild()
    block(boxElement)
}
