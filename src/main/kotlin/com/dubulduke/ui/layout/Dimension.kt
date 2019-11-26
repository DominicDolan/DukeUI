package com.dubulduke.ui.layout

internal class Dimension {
    private val ORIGIN = 0
    private val END = 1
    private val SIZE = 2
    private val CENTER = 3

    private var changed = true

    var origin: Double = 0.0
        set(value) {
            prepareToSet(ORIGIN,value != field)
            field = value
        }

    var end: Double = 0.0
        set(value) {
            prepareToSet(END,value != field)
            field = value
        }

    var size: Double = 0.0
        set(value) {
            prepareToSet(SIZE,value != field)
            field = value
        }

    var center: Double = 0.0
        set(value) {
            prepareToSet(CENTER,value != field)
            field = value
        }

    private var firstPriority: Int = ORIGIN
    private var secondPriority: Int = SIZE

    private fun prepareToSet(newPriority: Int, hasValueChanged: Boolean) {
        changed = hasValueChanged || (firstPriority != newPriority) || changed
        shiftPriorities(newPriority)
    }

    private fun shiftPriorities(newPriority: Int) {
        if (firstPriority != newPriority) {
            secondPriority = firstPriority
        }
        firstPriority = newPriority
    }

    private val output = Output(0.0, 1.0)

    val calculatedOrigin: Double
        get() {
            if (changed) { set() }
            return output.origin
        }

    val calculatedSize: Double
        get() {
            if (changed) { set() }
            return output.size
        }

    private fun set() {
        val fp = firstPriority
        val sp = secondPriority
        fun isFirstAndSecondPriority(i1: Int, i2: Int) = (fp == i1 && sp == i2) || (fp == i2 && sp == i1)

        when {
            isFirstAndSecondPriority(ORIGIN, SIZE) -> {
                output.origin = origin
                output.size = size
            }
            isFirstAndSecondPriority(ORIGIN, END) -> {
                output.origin = origin
                output.size = end - origin
            }
            isFirstAndSecondPriority(ORIGIN, CENTER) -> {
                output.origin = origin
                output.size = (center - origin)*2.0
            }
            isFirstAndSecondPriority(SIZE, END) -> {
                output.size = size
                output.origin = end - size
            }
            isFirstAndSecondPriority(SIZE, CENTER) -> {
                output.size = size
                output.origin = center - size/2.0
            }
            isFirstAndSecondPriority(END, CENTER) -> {
                output.size = (end - center)*2
                output.origin = end - output.size
            }
        }
        changed = false
    }

    fun resetPriorities() {
        firstPriority = ORIGIN
        secondPriority = SIZE
    }

    private inner class Output(var origin: Double, var size: Double)
}