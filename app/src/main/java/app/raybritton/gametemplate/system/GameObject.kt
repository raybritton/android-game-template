package app.raybritton.gametemplate.system

import android.graphics.Canvas
import app.raybritton.gametemplate.ext.translate

abstract class GameObject(
    var originX: Float,
    var originY: Float
) {

    abstract val name: String

    abstract val description: String

    abstract fun load()

    abstract fun update(currentTime: Long, delta: Float)

    abstract fun render(c: Canvas)

    fun renderWithTranslate(c: Canvas) {
        c.translate(originX, originY) {
            render(c)
        }
    }

    protected open fun onTap(x: Float, y: Float): Boolean {
        return false
    }

    protected open fun onTouchOutside(x: Float, y: Float) {}
    protected open fun onPointerUp(x: Float, y: Float) {}
    protected open fun onLongPress(x: Float, y: Float) {}
    protected open fun onPress(x: Float, y: Float) {}
    protected open fun onDrag(originX: Float, originY: Float, newX: Float, newY: Float) {}

    open fun resize(width: Float, height: Float) {}

    fun handleTap(x: Float, y: Float): Boolean {
        return onTap(x - originX, y - originY)
    }

    fun handlePointerUp(x: Float, y: Float) {
        onPointerUp(x - originX, y - originY)
    }

    fun handleLongPress(x: Float, y: Float) {
        onLongPress(x - originX, y - originY)
    }

    fun handlePress(x: Float, y: Float) {
        onPress(x - originX, y - originY)
    }

    fun handleTouchOutside(x: Float, y: Float) {
        onTouchOutside(x - originX, y - originY)
    }

    fun handleDrag(originX: Float, originY: Float, newX: Float, newY: Float) {
        onDrag(originX - this.originX, originY - this.originY, newX - this.originX, newY - this.originY)
    }
}