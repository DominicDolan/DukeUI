package com.dubulduke.ui.layout

internal class Dimension(private val isForwards: Boolean, private val viewportSize: Double) {
    private val MINIMUM = 0
    private val MAXIMUM = 1
    private val SIZE = 2
    private val CENTER = 3

    private val switchValues = !(isForwards && viewportSize >= 0)

    private val minimumIsAtTop = switchValues
    private val maximumIsAtTop = !switchValues

    private var changed = true

    private var start = 0.0
    private var end = 0.0

    var minimum: Double
        get() = start
        set(value) {
            if (maximumIsAtTop) {
                shiftPriorities(MINIMUM)
                start = value
            } else {
                shiftPriorities(MAXIMUM)
                end = value
            }
        }

    var maximum: Double
        get() = end
        set(value) {
            if (maximumIsAtTop) {
                shiftPriorities(MAXIMUM)
                end = value
            } else {
                shiftPriorities(MINIMUM)
                start = value
            }
        }

    var size: Double = 0.0
        set(value) {
            shiftPriorities(SIZE)
            field = value
        }

    var center: Double = 0.0
        set(value) {
            shiftPriorities(CENTER)
            field = value
        }

    private var firstPriority: Int = MINIMUM
    private var secondPriority: Int = SIZE

    private fun shiftPriorities(newPriority: Int) {
        changed = true
        if (firstPriority != newPriority) {
            secondPriority = firstPriority
        }
        firstPriority = newPriority
    }

    private val output = Output(0.0, 1.0)

    val outputPosition: Double
        get() {
            if (changed) { set() }
            return output.minimum
        }

    val outputSize: Double
        get() {
            if (changed) { set() }
            return output.size
        }

    val outputMax: Double
        get() = maximum
    val outputMin: Double
        get() = minimum

    private fun set() {
        val fp = firstPriority
        val sp = secondPriority
        fun isFirstAndSecondPriority(i1: Int, i2: Int) = (fp == i1 && sp == i2) || (fp == i2 && sp == i1)

        when {
            isFirstAndSecondPriority(MINIMUM, SIZE) -> {
                output.minimum = minimum
                output.size = size
            }
            isFirstAndSecondPriority(MINIMUM, MAXIMUM) -> {
                output.minimum = minimum
                output.size = maximum - minimum
            }
            isFirstAndSecondPriority(MINIMUM, CENTER) -> {
                output.minimum = minimum
                output.size = (center - minimum)*2.0
            }
            isFirstAndSecondPriority(SIZE, MAXIMUM) -> {
                output.size = size
                output.minimum = maximum - size
            }
            isFirstAndSecondPriority(SIZE, CENTER) -> {
                output.size = size
                output.minimum = center - size/2.0
            }
            isFirstAndSecondPriority(MAXIMUM, CENTER) -> {
                output.size = (maximum - center)*2
                output.minimum = maximum - output.size
            }
        }
        minimum = output.minimum
        maximum = output.minimum + output.size
        changed = false
    }

    fun resetPriorities() {
        firstPriority = MINIMUM
        secondPriority = SIZE
    }

    private inner class Output(var minimum: Double, var size: Double)
}