package com.dubulduke.ui

import com.dubulduke.ui.layout.EditableLayout
import com.dubulduke.ui.layout.Layout
import com.dubulduke.ui.style.EditableStyle
import com.dubulduke.ui.style.Style

class RenderDescription(style: EditableStyle, layout: EditableLayout) : Style by style, Layout by layout {

    override fun toString(): String {
        return "x: $x, y: $y, width: $width, height: $height"
    }
}