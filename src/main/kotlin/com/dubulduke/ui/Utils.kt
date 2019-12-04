package com.dubulduke.ui

import com.dubulduke.ui.layout.BaseLayout
import com.dubulduke.ui.layout.Layout

fun createLayout(setLayout: Layout.(parent: BaseLayout, sibling: BaseLayout) -> Unit) = setLayout