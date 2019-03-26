package app.raybritton.gametemplate.view.dsl

import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.view.BaseLayout
import app.raybritton.gametemplate.view.ToggleButton
import app.raybritton.gametemplate.view.View

fun BaseLayout.toggleButton(text: CharSequence, onClick: (View) -> Unit, block: (ToggleButton.() -> Unit)? = null): ToggleButton {
    val button = ToggleButton(text)
    button.onClick = onClick
    button.leftPadding = Dimen.dpToPx(4)
    button.rightPadding = Dimen.dpToPx(4)
    block?.invoke(button)
    addChild(button)
    return button
}
