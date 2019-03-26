package app.raybritton.gametemplate

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.system.FPoint
import app.raybritton.gametemplate.system.GameThread
import app.raybritton.gametemplate.system.Point
import app.raybritton.gametemplate.system.Scene
import java.util.concurrent.CopyOnWriteArrayList

class TouchTestScene(game: GameThread) : Scene(game) {
    override val name = "touch test"

    private val gridPaint = Paint().apply {
        color = Color.argb(50, 255, 255, 255)
        strokeWidth = 1f
        style = Paint.Style.STROKE
    }

    private val info = buildTextPaint(Color.WHITE, 14, Paint.Align.LEFT)

    private val dot = buildPaint(Color.BLUE, Paint.Style.FILL, 0)

    private val cellSize = Dimen.dpToPx(100)
    private val camera = Point(0, 0)
    private var scale = 1f

    private val presses = CopyOnWriteArrayList<FPoint>()
    private val taps = CopyOnWriteArrayList<FPoint>()
    private val longPresses = CopyOnWriteArrayList<FPoint>()
    private val drags = CopyOnWriteArrayList<Pair<FPoint, FPoint>>()
    private val flings = CopyOnWriteArrayList<Pair<FPoint, FPoint>>()

    override fun loadScene(data: Map<String, Any>) {

    }

    override fun showPress(x: Float, y: Float) {
        presses.add(FPoint(x, y))
    }

    override fun handleTap(x: Float, y: Float) {
        taps.add(FPoint(x, y))
    }

    override fun handleFling(x1: Float, y1: Float, x2: Float, y2: Float) {
        flings.add(FPoint(x1, y1) to FPoint(x2, y2))
    }

    override fun handleDrag(originX: Float, originY: Float, currentX: Float, currentY: Float) {
        drags.add(FPoint(originX, originY) to FPoint(currentX, currentY))
    }

    override fun handleLongPress(x: Float, y: Float) {
        longPresses.add(FPoint(x, y))
    }

    override fun handleScale(factor: Float) {
        scale = Math.max(Math.min(scale * factor, 0.5f), 2f)
    }

    override fun update(currentTime: Long, deltaTime: Float) {

    }

    override fun render(canvas: Canvas, currentTime: Long) {
        canvas.drawColor(Color.parseColor("#1565C0"))
        canvas.drawText("Scale: $scale", 30f, 30f, info)
        dot.color = Color.GREEN
        presses.forEach {
            canvas.drawCircle(it.x, it.y, 4f, dot)
            canvas.drawText("Press", it.x + 4, it.y - 4, info)
        }

        taps.forEach {
            canvas.drawCircle(it.x, it.y, 4f, dot)
            canvas.drawText("Tap", it.x + 4, it.y + 14, info)
        }

        longPresses.forEach {
            canvas.drawCircle(it.x, it.y, 4f, dot)
            canvas.drawText("Long Press", it.x + 4, it.y + 14, info)
        }

        drags.forEach {
            canvas.drawText("Drag", it.first.x - it.second.x, it.first.y - it.second.y, info)
            canvas.drawLine(it.first.x, it.first.y, it.second.x, it.second.y, dot)
        }

        drags.forEach {
            canvas.drawText("Fling", it.first.x - it.second.x, it.first.y - it.second.y, info)
            canvas.drawLine(it.first.x, it.first.y, it.second.x, it.second.y, dot)
        }
    }

}