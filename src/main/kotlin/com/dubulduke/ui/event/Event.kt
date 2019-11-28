package com.dubulduke.ui.event

interface Event {
    fun onClick(callback: () -> Unit)
    fun onClickRelease(callback: () -> Unit)
    fun onHover(callback: () -> Unit)
}