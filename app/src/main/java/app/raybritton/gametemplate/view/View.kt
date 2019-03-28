package app.raybritton.gametemplate.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.ext.translateAndClip

abstract class View(protected val parent: Layout) {
    //The top left corner
    var x: Float = 0f
    //The top left corner
    var y: Float = 0f
    //Generally this shouldn't be set or get
    var w: Float = 0f
        set(value) {
            field = Math.max(0f, value)
        }
    //Generally this shouldn't be set or get
    var h: Float = 0f
        set(value) {
            field = Math.max(0f, value)
        }
    var onClick: ((View) -> Unit)? = null
    var topPadding: Float = 0f
    var leftPadding: Float = 0f
    var rightPadding: Float = 0f
    var bottomPadding: Float = 0f
    //Automatically drawn, adjust at any time to change the background
    val background = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.TRANSPARENT
        strokeWidth = Dimen.dpToPx(1)
    }
    lateinit var layoutParams: LayoutParams

    // The width of just the content
    fun contentWidth() = Math.max(0f, widthWithPadding() - leftPadding - rightPadding)
    // The height of just the content
    fun contentHeight() = Math.max(0f, heightWithPadding() - topPadding - bottomPadding)

    // The width of the content and padding
    fun widthWithPadding() = w
    // The height of the content and padding
    fun heightWithPadding() = h

    // The width of the content, padding and margins
    fun widthWithMargins() = Math.max(0f, widthWithPadding() + layoutParams.leftMargin + layoutParams.rightMargin)
    // The height of the content, padding and margins
    fun heightWithMargins() = Math.max(0f, heightWithPadding() + layoutParams.topMargin + layoutParams.bottomMargin)

    fun render(c: Canvas) {
        c.translateAndClip(x, y, widthWithPadding(), heightWithPadding()) {
            c.drawRect(0f, 0f, widthWithPadding() - 1f, heightWithPadding() - 1f, background)
            if (debugBounds) {
                c.drawRect(
                    1f,
                    1f,
                    widthWithPadding() - 1,
                    heightWithPadding() - 1,
                    debugPaint.apply { color = if (this@View is Layout) Color.YELLOW else Color.RED })
            }
            translateAndClip(leftPadding, topPadding, w, h) {
                draw(c)
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
        //lazily get content size as calculations could be expensive
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

    /**
     * Returns the size of the content
     * maxW: Maximum width of content
     * maxH: Maximum height of content
     *
     * If the size is greater than the maximums then it will be cut off
     */
    protected abstract fun getContentSize(maxW: Float, maxH: Float): PointF

    /**
     * After being laid out and measured this is called with the width and height available to draw in
     */
    abstract fun onMeasured(contentWidth: Float, contentHeight: Float)

    companion object {
        //If true, all layouts and views will have a box drawn on their edges (size with margins)
        var debugBounds = false

        private val debugPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = Dimen.dpToPx(1)
            color = Color.RED
            isAntiAlias = true
        }
    }
}