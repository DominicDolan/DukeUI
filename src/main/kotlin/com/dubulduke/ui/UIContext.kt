package com.dubulduke.ui

import com.dubulduke.ui.element.Element
import com.dubulduke.ui.element.ElementData
import com.dubulduke.ui.event.Event
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.render.DefaultElementRenderer
import com.dubulduke.ui.render.RenderDescription
import kotlin.math.abs

class UIContext<S, E: Event>(
        val window: Window,
        val viewport: Viewport,
        val elementData: ElementData<S, E>
) {
    var userData: Any? = null
        private set

    val leftIsLow: Boolean
    val bottomIsLow: Boolean

    init {
        val horizontalIsRightwards = window.right > window.left
        val viewportWidthIsPositive = viewport.width >= 0
        leftIsLow = !(horizontalIsRightwards xor viewportWidthIsPositive)

        val verticalIsUpwards = window.top > window.bottom
        val viewportHeightIsPositive = viewport.height >= 0
        bottomIsLow = !(verticalIsUpwards xor viewportHeightIsPositive)

    }

    fun createBaseElement() = BaseElement()

    fun setUserData(userData: Any?) {
        this.userData = userData
    }

    inline fun <reified T> useUserData(use: (T) -> Unit) {
        val data = userData
        if (data != null && data is T) {
            use(data)
        }
    }

    inner class BaseElement : Element<S, E>(this, emptyRenderer) {

        init {
            val baseLayout = object : BaseLayout(this@UIContext) {
                override val x = viewport.x
                override val y = viewport.y
                override val width = abs(viewport.width)
                override val height = abs(viewport.height)
            }

            setLayout(baseLayout)
            setParentLayout(baseLayout)
        }

        public override fun renderLayout(depth: Int) {
            super.renderLayout(depth)
        }
    }

    val emptyRenderer = object : DefaultElementRenderer<S, E> {
        override fun draw(description: RenderDescription<S>, data: Any?) { }

    }

}