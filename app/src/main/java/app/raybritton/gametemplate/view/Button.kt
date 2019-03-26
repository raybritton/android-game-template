package app.raybritton.gametemplate.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.ext.translate

open class Button(val text: CharSequence) : View() {
    val textPaint = TextPaint().apply {
        color = Color.WHITE
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

    private val internalPadding = Dimen.dpToPx(2)

    private lateinit var textLayout: StaticLayout

    override fun draw(c: Canvas) {
        c.translate(internalPadding, internalPadding) {
            textLayout.draw(c)
        }
        c.drawRect(0f, 0f, contentWidth(), contentHeight(), strokePaint)
    }

    override fun onUpdate(currentTime: Long, delta: Float) {

    }

    override fun getContentSize(maxW: Float, maxH: Float): PointF {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, maxW.toInt())
            .build()
        val textWidth = (0 until textLayout.lineCount).map { textLayout.getLineWidth(it) }.max() ?: 0f
        return PointF(textWidth + internalPadding + internalPadding, textLayout.height.toFloat() + internalPadding + internalPadding)
    }

    override fun onMeasured(contentWidth: Float, contentHeight: Float) {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, contentWidth.toInt())
            .build()
    }
}