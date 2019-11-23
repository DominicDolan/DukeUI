package com.dubulduke.ui

import com.dubulduke.ui.layout.Layout

data class Viewport(
        override val x: Double,
        override val y: Double,
        override val width: Double,
        override val height: Double) : Layout