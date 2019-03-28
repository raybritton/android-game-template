package app.raybritton.gametemplate.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.text.StaticLayout
import android.text.TextPaint
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.ext.translate
import timber.log.Timber

/**
 * parent is the layout this view is contained in
 * text is the text that will appear on the button
 * toggleGroup is used to automatically untoggle other togglebuttons with the same toggleGroup, ignored if null
 */
class ToggleButton(parent: BaseLayout, val text: CharSequence, val toggleGroup: String? = null) : View(parent) {
    val textPaint = TextPaint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        textSize = Dimen.dpToPx(20)
    }
    val strokePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = Dimen.dpToPx(2)
        isAntiAlias = true
    }
    val fillPaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = Dimen.dpToPx(2)
        isAntiAlias = true
    }

    var selected = false

    private var otherToggleButtons = listOf<ToggleButton>()
    private val internalPadding = Dimen.dpToPx(2)

    private lateinit var textLayout: StaticLayout

    override fun draw(c: Canvas) {
        if (selected) {
            c.drawRect(0f, 0f, contentWidth(), contentHeight(), fillPaint)
        } else {
            c.drawRect(0f, 0f, contentWidth(), contentHeight(), strokePaint)
        }
        c.translate(internalPadding, internalPadding) {
            textLayout.draw(c)
        }
    }

    override fun handleTap(x: Float, y: Float): Boolean {
        if (super.handleTap(x, y)) {
            selected = !selected
            Timber.d("selected: $selected")
            if (selected) {
                otherToggleButtons.forEach { it.selected = false }
            }
            return true
        } else {
            return false
        }
    }

    override fun onUpdate(currentTime: Long, delta: Float) {

    }

    override fun getContentSize(maxW: Float, maxH: Float): PointF {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, maxW.toInt())
            .build()
        val textWidth = (0 until textLayout.lineCount).map { textLayout.getLineWidth(it) }.max() ?: 0f
        return PointF(
            textWidth + internalPadding + internalPadding,
            textLayout.height.toFloat() + internalPadding + internalPadding
        )
    }

    override fun onMeasured(contentWidth: Float, contentHeight: Float) {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, contentWidth.toInt())
            .build()

        if (toggleGroup != null) {
            otherToggleButtons = parent.children()
                .filter { it is ToggleButton && it != this && it.toggleGroup == this.toggleGroup }
                .map { it as ToggleButton }
                .toList()
        }
    }
}