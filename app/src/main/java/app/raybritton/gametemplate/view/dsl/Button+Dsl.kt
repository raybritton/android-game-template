package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.view.BaseLayout
import app.raybritton.gametemplate.view.Button
import app.raybritton.gametemplate.view.View

fun BaseLayout.button(text: CharSequence, onClick: (View) -> Unit, block: (Button.() -> Unit)? = null): Button {
    val button = Button(this, text)
    button.onClick = onClick
    button.leftPadding = Dimen.dpToPx(4)
    button.rightPadding = Dimen.dpToPx(4)
    block?.invoke(button)
    addChild(button)
    return button
}