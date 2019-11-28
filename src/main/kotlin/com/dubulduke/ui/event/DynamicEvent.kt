package com.dubulduke.ui.event

import com.dubulduke.ui.input.MouseCallback
import com.dubulduke.ui.render.RenderDescription
import kotlin.math.abs
import kotlin.math.sign

@Suppress("OVERRIDE_BY_INLINE")
class DynamicEvent(private val description: RenderDescription, private val callback: MouseCallback) {
    private val mouseX: Double
        get() = callback.mouseX
    private val mouseY: Double
        get() = callback.mouseY

    private val clickInsideBoolean = DynamicBoolean {
        callback.leftMouseButton && hoverBoolean()
    }

    private val hoverBoolean = DynamicBoolean {
        val adjustX = mouseX - sign(description.width)*description.x
        val adjustY = mouseY - sign(description.height)*description.y
        adjustX < abs(description.width) && adjustX > 0 &&
        adjustY < abs(description.height) && adjustY > 0
    }

    val hover: Boolean
        get() = hoverBoolean()

    val hoverEnd: Boolean
        get() = hoverBoolean.hasBeenFalse

    val hoverStart: Boolean
        get() = hoverBoolean.hasBeenTrue

    val clickIsPressed: Boolean
        get() = clickInsideBoolean()

    val clickWasPressed: Boolean
        get() = clickInsideBoolean.hasBeenTrue

    val clickWasReleased: Boolean
        get() = clickInsideBoolean.hasBeenFalse

    internal fun update() {
        clickInsideBoolean.update()
        hoverBoolean.update()
    }
}