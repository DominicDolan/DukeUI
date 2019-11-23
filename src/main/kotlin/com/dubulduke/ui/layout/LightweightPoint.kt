package com.dubulduke.ui.layout

import java.lang.Float.intBitsToFloat

inline class LightweightPoint(private val xy: Long): Point {
    override val x get() = intBitsToFloat((xy shr 32).toInt()).toDouble()
    override val y get() = intBitsToFloat(xy.toInt()).toDouble()

    override fun toString(): String {
        return "($x, $y)"
    }
}