package app.raybritton.gametemplate.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.text.Layout.*
import android.text.StaticLayout
import android.text.TextPaint
import app.raybritton.gametemplate.android.Dimen

class TextView(val text: CharSequence) : View() {
    val paint = TextPaint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL_AND_STROKE
        isAntiAlias = true
        textSize = Dimen.dpToPx(20)
    }

    private lateinit var textLayout: StaticLayout

    var alignment = Alignment.ALIGN_NORMAL

    override fun draw(c: Canvas) {
        textLayout.draw(c)
    }

    override fun onUpdate(currentTime: Long, delta: Float) {

    }

    override fun getContentSize(maxW: Float, maxH: Float): PointF {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, paint, maxW.toInt())
            .setAlignment(alignment)
            .build()
        val textWidth = (0 until textLayout.lineCount).map { textLayout.getLineWidth(it) }.max() ?: 0f
        return PointF(textWidth, textLayout.height.toFloat())
    }

    override fun onMeasured(contentWidth: Float, contentHeight: Float) {
        textLayout = StaticLayout.Builder.obtain(text, 0, text.length, paint, contentWidth.toInt())
            .setAlignment(alignment)
            .build()
    }
}