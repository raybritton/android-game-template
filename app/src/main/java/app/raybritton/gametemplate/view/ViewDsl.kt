package app.raybritton.gametemplate.view

import android.graphics.Color
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.system.Scene
import app.raybritton.gametemplate.view.FrameLayout.FrameLayoutParams

fun Scene.frameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val layout = FrameLayout(this)
    layout.background = Color.BLACK
    layout.layoutParams = layout.frameLayoutParams(
        Size.MATCH_PARENT,
        Size.MATCH_PARENT,
        FrameLayoutParams.Anchor.CENTER,
        FrameLayoutParams.Anchor.CENTER
    )
    block(layout)
    return layout
}

fun Scene.linearLayout(orientation: LinearLayout.Orientation, block: LinearLayout.() -> Unit): LinearLayout {
    val layout = LinearLayout(this, orientation)
    layout.background = Color.BLACK
    layout.layoutParams = layout.frameLayoutParams(
        Size.MATCH_PARENT,
        Size.MATCH_PARENT,
        FrameLayoutParams.Anchor.CENTER,
        FrameLayoutParams.Anchor.CENTER
    )
    block(layout)
    return layout
}

fun BaseLayout.frameLayout(block: FrameLayout.() -> Unit): FrameLayout {
    val layout = FrameLayout(this)
    block(layout)
    addChild(layout)
    return layout
}

fun BaseLayout.linearLayout(orientation: LinearLayout.Orientation, block: LinearLayout.() -> Unit): LinearLayout {
    val layout = LinearLayout(this, orientation)
    block(layout)
    addChild(layout)
    return layout
}

fun BaseLayout.textView(text: CharSequence, block: (TextView.() -> Unit)? = null): TextView {
    val textView = TextView(text)
    block?.invoke(textView)
    addChild(textView)
    return textView
}

fun View.frameLayoutParams(
    width: Size = Size.WRAP_CONTENT,
    height: Size = Size.WRAP_CONTENT,
    parentAnchor: FrameLayoutParams.Anchor,
    childAnchor: FrameLayoutParams.Anchor = parentAnchor,
    block: (FrameLayoutParams.() -> Unit)? = null
): FrameLayoutParams {
    val params = FrameLayoutParams(width, height)
    layoutParams = params
    params.parentAnchor = parentAnchor
    params.childAnchor = childAnchor
    block?.invoke(params)
    return params
}

fun View.linearLayoutParams(
    width: Size = Size.WRAP_CONTENT,
    height: Size = Size.WRAP_CONTENT,
    block: (LinearLayout.LinearLayoutParams.() -> Unit)? = null
): LinearLayout.LinearLayoutParams {
    val params = LinearLayout.LinearLayoutParams(width, height)
    layoutParams = params
    if (block != null) {
        params.block()
    }
    return params
}

fun BaseLayout.button(text: CharSequence, onClick: (View) -> Unit, block: (Button.() -> Unit)? = null): Button {
    val button = Button(text)
    button.onClick = onClick
    button.leftPadding = Dimen.dpToPx(4)
    button.rightPadding = Dimen.dpToPx(4)
    block?.invoke(button)
    addChild(button)
    return button
}


fun BaseLayout.toggleButton(text: CharSequence, onClick: (View) -> Unit, block: (ToggleButton.() -> Unit)? = null): ToggleButton {
    val button = ToggleButton(text)
    button.onClick = onClick
    button.leftPadding = Dimen.dpToPx(4)
    button.rightPadding = Dimen.dpToPx(4)
    block?.invoke(button)
    addChild(button)
    return button
}
