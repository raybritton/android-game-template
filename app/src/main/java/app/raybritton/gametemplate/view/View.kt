package app.raybritton.gametemplate.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.ext.translateAndClip

abstract class View {
    var x: Float = 0f
    var y: Float = 0f
    var w: Float = 0f
        set(value) {
            field = Math.max(0f, value)
        }
    var h: Float = 0f
        set(value) {
            field = Math.max(0f, value)
        }
    var onClick: ((View) -> Unit)? = null
    var topPadding: Float = 0f
    var leftPadding: Float = 0f
    var rightPadding: Float = 0f
    var bottomPadding: Float = 0f
    var background: Int = Color.TRANSPARENT
    lateinit var layoutParams: LayoutParams

    fun contentWidth() = Math.max(0f, widthWithPadding() - leftPadding - rightPadding)
    fun contentHeight() = Math.max(0f,heightWithPadding() - topPadding - bottomPadding)

    fun widthWithPadding() = w
    fun heightWithPadding() = h

    fun widthWithMargins() = Math.max(0f,widthWithPadding() + layoutParams.leftMargin + layoutParams.rightMargin)
    fun heightWithMargins() = Math.max(0f,heightWithPadding() + layoutParams.topMargin + layoutParams.bottomMargin)

    fun render(c: Canvas) {
        c.translateAndClip(x, y, widthWithPadding(), heightWithPadding()) {
            c.drawColor(background)
            translateAndClip(leftPadding, topPadding, w, h) {
                draw(c)
            }
            if (debugBounds) {
                c.drawRect(
                    1f,
                    1f,
                    widthWithPadding() - 1,
                    heightWithPadding() - 1,
                    debugPaint.apply { color = if (this@View is Layout) Color.YELLOW else Color.RED })
            }
        }
    }

    open fun handleTap(x: Float, y: Float): Boolean {
        if (onClick != null) {
            if (x > this.x && x < this.x + this.widthWithPadding() &&
                y > this.y && y < this.y + this.heightWithPadding()
            ) {
                onClick?.invoke(this)
                return true
            }
        }
        return false
    }

    protected abstract fun draw(c: Canvas)
    abstract fun onUpdate(currentTime: Long, delta: Float)
    fun measure(parent: Layout) {
        val contentSize by lazy { getContentSize(parent.sizingWidth(), parent.sizingHeight()) }
        w = when (layoutParams.width) {
            is Size.EXACT -> (layoutParams.width as Size.EXACT).px
            Size.MATCH_PARENT -> parent.contentWidth() - (layoutParams.leftMargin) - (layoutParams.rightMargin)
            Size.WRAP_CONTENT -> contentSize.x + leftPadding + rightPadding
        }
        h = when (layoutParams.height) {
            is Size.EXACT -> (layoutParams.height as Size.EXACT).px
            Size.MATCH_PARENT -> parent.contentHeight() - (layoutParams.topMargin) - (layoutParams.bottomMargin)
            Size.WRAP_CONTENT -> contentSize.y + topPadding + bottomPadding
        }
        onMeasured(contentWidth(), contentHeight())
    }

    protected abstract fun getContentSize(maxW: Float, maxH: Float): PointF
    abstract fun onMeasured(contentWidth: Float, contentHeight: Float)

    companion object {
        var debugBounds = false

        private val debugPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = Dimen.dpToPx(1)
            color = Color.RED
            isAntiAlias = true
        }
    }
}