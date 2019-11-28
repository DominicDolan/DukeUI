package com.dubulduke.ui.render

internal class Renderer<T>(private val renderOperation: RenderDescription.(T) -> Unit) {
    var renderObject: T? = null

    fun render(description: RenderDescription) {
        val renderObject = this.renderObject
        if (renderObject != null) {
            renderOperation(description, renderObject)
        }
    }
}