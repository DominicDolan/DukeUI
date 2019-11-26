package com.dubulduke.ui

class DynamicUIOptions<T>(internal var renderOperation: RenderDescription.(T) -> Unit) {
    internal var window = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    internal var viewport = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    internal var renderer: T? = null
        private set

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

    fun setRenderer(renderer: T): DynamicUIOptions<T> {
        this.renderer = renderer
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