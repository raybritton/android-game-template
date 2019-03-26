package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.view.FrameLayout
import app.raybritton.gametemplate.view.Size
import app.raybritton.gametemplate.view.View

fun View.frameLayoutParams(
    width: Size = Size.WRAP_CONTENT,
    height: Size = Size.WRAP_CONTENT,
    parentAnchor: FrameLayout.FrameLayoutParams.Anchor,
    childAnchor: FrameLayout.FrameLayoutParams.Anchor = parentAnchor,
    block: (FrameLayout.FrameLayoutParams.() -> Unit)? = null
): FrameLayout.FrameLayoutParams {
    val params = FrameLayout.FrameLayoutParams(width, height)
    layoutParams = params
    params.parentAnchor = parentAnchor
    params.childAnchor = childAnchor
    block?.invoke(params)
    return params
}
