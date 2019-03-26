package app.raybritton.gametemplate.ext

import android.graphics.Canvas

fun Canvas.translate(x: Float, y: Float, block: Canvas.() -> Unit) {
    save()
    translate(x, y)
    block()
    restore()
}

fun Canvas.clip(w: Float, h: Float, block: Canvas.() -> Unit) {
    save()
    clipRect(0f, 0f, w, h)
    block()
    restore()
}

fun Canvas.translateAndClip(x: Float, y: Float, w: Float, h: Float, block: Canvas.() -> Unit) {
    save()
    translate(x, y)
    clipRect(0f, 0f, w, h)
    block()
    restore()
}

fun Canvas.rotate(x: Float, y: Float, degrees: Float, block: Canvas.() -> Unit) {
    save()
    rotate(degrees, x, y)
    block()
    restore()
}