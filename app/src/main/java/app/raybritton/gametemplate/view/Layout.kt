package app.raybritton.gametemplate.view

import android.graphics.Canvas

interface Layout {
    fun contentWidth(): Float
    fun contentHeight(): Float
    val isRootLayout: Boolean
    fun sizingWidth(): Float
    fun sizingHeight(): Float
}

abstract class BaseLayout(private val parent: Layout) : View(), Layout {
    override val isRootLayout = false
    protected val children = mutableListOf<View>()

    override fun sizingWidth(): Float {
        return if (layoutParams.width == Size.WRAP_CONTENT) {
            parent.sizingWidth()
        } else {
            contentWidth()
        }
    }

    override fun sizingHeight(): Float {
        return if (layoutParams.height == Size.WRAP_CONTENT) {
            parent.sizingHeight()
        } else {
            contentHeight()
        }
    }

    fun addChild(view: View) {
        children.add(view)
        measure(parent)
        layout()
    }

    fun removeChild(view: View) {
        children.add(view)
        measure(parent)
        layout()
    }

    override fun draw(c: Canvas) {
        children.forEach { it.render(c) }
    }

    override fun onUpdate(currentTime: Long, delta: Float) {
        children.forEach { it.onUpdate(currentTime, delta) }
    }

    abstract fun layout()

    override fun onMeasured(contentWidth: Float, contentHeight: Float) {
        children.forEach { it.measure(this) }
    }

    override fun handleTap(x: Float, y: Float): Boolean {
        val adjustedX = x - this.x
        val adjustedY = y - this.y
        return if (children.firstOrNull { it.handleTap(adjustedX, adjustedY) } != null) {
            true
        } else {
            super.handleTap(adjustedX, adjustedY)
        }
    }
}

abstract class LayoutParams(val width: Size, val height: Size) {
    var topMargin: Float = 0f
    var leftMargin: Float = 0f
    var rightMargin: Float = 0f
    var bottomMargin: Float = 0f
}

sealed class Size {
    class EXACT(val px: Float) : Size()
    object MATCH_PARENT : Size()
    object WRAP_CONTENT : Size()
}