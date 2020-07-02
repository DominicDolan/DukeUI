package com.dubulduke.ui

import com.dubulduke.ui.layout.EditLayout
import com.dubulduke.ui.layout.Layout

fun createLayout(setLayout: EditLayout.(parent: Layout, sibling: Layout) -> Unit) = setLayout
