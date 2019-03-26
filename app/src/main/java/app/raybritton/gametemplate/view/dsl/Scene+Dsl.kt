package app.raybritton.gametemplate.view.dsl

import android.graphics.Color
import app.raybritton.gametemplate.system.Scene
import app.raybritton.gametemplate.view.FrameLayout
import app.raybritton.gametemplate.view.LinearLayout
import app.raybritton.gametemplate.view.LinearLayout.*
import app.raybritton.gametemplate.view.Size

fun Scene.frameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val layout = FrameLayout(this)
    layout.background.color = Color.BLACK
    layout.layoutParams = layout.frameLayoutParams(
        Size.MATCH_PARENT,
        Size.MATCH_PARENT,
        FrameLayout.FrameLayoutParams.Anchor.CENTER,
        FrameLayout.FrameLayoutParams.Anchor.CENTER
    )
    block(layout)
    return layout
}

fun Scene.linearLayout(orientation: LinearLayout.Orientation = Orientation.VERTICAL, block: LinearLayout.() -> Unit): LinearLayout {
    val layout = LinearLayout(this, orientation)
    layout.background.color = Color.BLACK
    layout.layoutParams = layout.frameLayoutParams(
        Size.MATCH_PARENT,
        Size.MATCH_PARENT,
        FrameLayout.FrameLayoutParams.Anchor.CENTER,
        FrameLayout.FrameLayoutParams.Anchor.CENTER
    )
    block(layout)
    return layout
}