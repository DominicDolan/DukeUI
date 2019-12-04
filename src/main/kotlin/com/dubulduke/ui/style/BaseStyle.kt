package com.dubulduke.ui.style

import com.dubulduke.ui.layout.Adjustment

interface BaseStyle {
    val color: Long
    val text: String
    val fontSize: Double

    fun visualLayout(setAdjustment: Adjustment.() -> Unit)
}