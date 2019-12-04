package com.dubulduke.ui.style

import com.dubulduke.ui.layout.Adjustment

class Style : BaseStyle {
    override var color: Long = 0xFFFFFF00
    override var text: String = ""
    override var fontSize: Double = 1.0

    internal val adjustment = Adjustment()
    override fun visualLayout(setAdjustment: Adjustment.() -> Unit) {
        setAdjustment(adjustment)
    }
}