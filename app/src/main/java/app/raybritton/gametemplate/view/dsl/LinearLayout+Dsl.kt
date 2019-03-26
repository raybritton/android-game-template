package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.view.LinearLayout
import app.raybritton.gametemplate.view.Size
import app.raybritton.gametemplate.view.View

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