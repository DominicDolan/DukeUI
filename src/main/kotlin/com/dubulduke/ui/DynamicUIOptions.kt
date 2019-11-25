package com.dubulduke.ui

class DynamicUIOptions<T>(internal var renderOperation: RenderDescription.(T) -> Unit) {
    internal var window = Viewport(0.0, 0.0, 1.0, 1.0)
    internal var viewport = Viewport(0.0, 0.0, 1.0, 1.0)
    internal var renderer: T? = null

    fun setWindow(x: Double, y: Double, width: Double, height: Double): DynamicUIOptions<T> {
        return setWindow(Viewport(x, y, width, height))
    }

    fun setWindow(window: Viewport): DynamicUIOptions<T> {
        this.window = window
        return this
    }

    fun setViewport(x: Double, y: Double, width: Double, height: Double): DynamicUIOptions<T> {
        return setViewport(Viewport(x, y, width, height))
    }

    fun setViewport(viewport: Viewport): DynamicUIOptions<T> {
        this.viewport = viewport
        return this
    }

    fun setRenderer(renderer: T): DynamicUIOptions<T> {
        this.renderer = renderer
        return this
    }
}