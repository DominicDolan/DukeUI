package com.dubulduke.ui

import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.style.Style
import com.dubulduke.ui.style.BaseStyle

class RenderDescription(style: Style, layout: Layout) : BaseStyle by style, BaseLayout by layout {

    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}