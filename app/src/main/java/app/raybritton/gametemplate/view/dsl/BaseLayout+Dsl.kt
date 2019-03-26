package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.view.BaseLayout
import app.raybritton.gametemplate.view.FrameLayout
import app.raybritton.gametemplate.view.LinearLayout
import app.raybritton.gametemplate.view.LinearLayout.*

fun BaseLayout.frameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val layout = FrameLayout(this)
    block(layout)
    addChild(layout)
    return layout
}

fun BaseLayout.linearLayout(orientation: LinearLayout.Orientation = Orientation.HORIZONTAL, block: LinearLayout.() -> Unit): LinearLayout {
    val layout = LinearLayout(this, orientation)
    block(layout)
    addChild(layout)
    return layout
}