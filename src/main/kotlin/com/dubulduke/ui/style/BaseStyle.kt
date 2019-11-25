package com.dubulduke.ui.style

import com.dubulduke.ui.layout.Adjustment

interface BaseStyle {
    val color: Long
    fun visualLayout(setAdjustment: Adjustment.() -> Unit)
}