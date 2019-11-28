package com.dubulduke.ui

import com.dubulduke.ui.input.MouseCallback
import com.dubulduke.ui.render.RenderDescription
import com.dubulduke.ui.render.Renderer

class DynamicUIOptions<T>(renderOperation: RenderDescription.(T) -> Unit) {
    internal var window = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    internal var viewport = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    internal var renderer = Renderer(renderOperation)
        private set
    internal var mouseCallback: MouseCallback = object : MouseCallback {
        override val mouseX: Double
            get() = 0.0
        override val mouseY: Double
            get() = 0.0
        override val leftMouseButton: Boolean
            get() = false
        override val rightMouseButton: Boolean
            get() = false

    }

    internal var yDirection = YDirection.DOWN
        private set
    internal var xDirection = XDirection.RIGHT
        private set

    fun setWindow(x: Double, y: Double, width: Double, height: Double,
                  yDirection: YDirection = YDirection.DOWN, xDirection: XDirection = XDirection.RIGHT)
            : DynamicUIOptions<T> {
        return setWindow(Viewport(x, y, width, height), yDirection, xDirection)
    }

    fun setWindow(window: Viewport,
                  yDirection: YDirection = YDirection.DOWN, xDirection: XDirection = XDirection.RIGHT): DynamicUIOptions<T> {
        this.window = window
        this.yDirection = yDirection
        this.xDirection = xDirection
        return this
    }

    fun setViewport(x: Double, y: Double, width: Double, height: Double): DynamicUIOptions<T> {
        return setViewport(Viewport(x, y, width, height))
    }

    fun setViewport(viewport: Viewport): DynamicUIOptions<T> {
        this.viewport = viewport
        return this
    }

    fun setMouseCallback(callback: MouseCallback): DynamicUIOptions<T> {
        this.mouseCallback = callback
        return this
    }

    fun setRenderObject(renderObject: T): DynamicUIOptions<T> {
        this.renderer.renderObject = renderObject
        return this
    }

    enum class YDirection {
        UP,
        DOWN
    }

    enum class XDirection {
        LEFT,
        RIGHT
    }
}