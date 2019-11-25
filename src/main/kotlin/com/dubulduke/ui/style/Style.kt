package com.dubulduke.ui.style

import com.dubulduke.ui.layout.Adjustment

class Style : BaseStyle {
    override var color: Long = 0xFFFFFF00

    internal val adjustment = Adjustment()
    override fun visualLayout(setAdjustment: Adjustment.() -> Unit) {
        setAdjustment(adjustment)
    }
}