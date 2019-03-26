package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.view.*
import app.raybritton.gametemplate.view.FrameLayout.FrameLayoutParams.Anchor

fun BaseLayout.textView(text: CharSequence, block: (TextView.() -> Unit)? = null): TextView {
    val textView = TextView(text)
    when (this) {
        is LinearLayout -> textView.layoutParams = textView.linearLayoutParams()
        is FrameLayout -> textView.layoutParams = textView.frameLayoutParams(parentAnchor = Anchor.TOP_LEFT)
    }
    block?.invoke(textView)
    addChild(textView)
    return textView
}