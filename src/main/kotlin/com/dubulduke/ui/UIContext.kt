package com.dubulduke.ui

import com.dubulduke.ui.element.RenderableElement
import com.dubulduke.ui.input.MouseCallback

class UIContext(private val base: (UIContext) -> RenderableElement) {
    internal var window = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    internal var viewport = Viewport(0.0, 0.0, 1.0, 1.0)
        private set
    var userData: Any? = null
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

    fun createBaseElement() = base(this)

    fun setWindow(x: Double, y: Double, width: Double, height: Double,
                  yDirection: YDirection = YDirection.DOWN, xDirection: XDirection = XDirection.RIGHT)
            : UIContext {
        return setWindow(Viewport(x, y, width, height), yDirection, xDirection)
    }

    fun setWindow(window: Viewport,
                  yDirection: YDirection = YDirection.DOWN, xDirection: XDirection = XDirection.RIGHT): UIContext {
        this.window = window
        this.yDirection = yDirection
        this.xDirection = xDirection
        return this
    }

    fun setViewport(x: Double, y: Double, width: Double, height: Double): UIContext {
        return setViewport(Viewport(x, y, width, height))
    }

    fun setViewport(viewport: Viewport): UIContext {
        this.viewport = viewport
        return this
    }

    fun setMouseCallback(callback: MouseCallback): UIContext {
        this.mouseCallback = callback
        return this
    }

    fun setUserData(userData: Any?): UIContext {
        this.userData = userData
        return this
    }

    inline fun <reified T> useUserData(use: (T) -> Unit) {
        val data = userData
        if (data != null && data is T) {
            use(data)
        }
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