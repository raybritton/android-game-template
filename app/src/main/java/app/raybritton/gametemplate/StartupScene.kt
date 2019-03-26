package app.raybritton.gametemplate

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.system.GameThread
import app.raybritton.gametemplate.system.Scene
import app.raybritton.gametemplate.view.*
import app.raybritton.gametemplate.view.FrameLayout.FrameLayoutParams.*
import app.raybritton.gametemplate.view.dsl.frameLayout
import app.raybritton.gametemplate.view.dsl.frameLayoutParams
import app.raybritton.gametemplate.view.dsl.textView

class StartupScene(game: GameThread) : Scene(game) {

    override val name = "Loading"

    private lateinit var loadingText: TextView
    private val loadingPaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL_AND_STROKE
    }

    private var delay = 2000f
    private var opened = false

    init {
        layout = frameLayout {
            loadingText = textView("Loading") {
                leftPadding = Dimen.dpToPx(8)
                rightPadding = Dimen.dpToPx(8)
                layoutParams = frameLayoutParams(Size.WRAP_CONTENT, Size.WRAP_CONTENT, Anchor.CENTER, Anchor.CENTER)
            }
        }
    }

    override fun loadScene(data: Map<String, Any>) {

    }

    override fun update(currentTime: Long, deltaTime: Float) {
        super.update(currentTime, deltaTime)
        if (delay > 0) {
            delay -= deltaTime
        }
        if (delay <= 0 && !opened) {
            opened = true
            game.loadScene(TextTestScene(game)) {

            }
        }
    }

    override fun render(canvas: Canvas, currentTime: Long) {
        super.render(canvas, currentTime)
        val progress = 1 - (delay / 5000f)
        val barW = progress * loadingText.contentWidth()
        canvas.drawRect(
            loadingText.x + loadingText.leftPadding,
            loadingText.y + loadingText.contentHeight(),
            loadingText.x + barW  + loadingText.leftPadding,
            loadingText.y + loadingText.contentHeight() + Dimen.dpToPx(4),
            loadingPaint
        )
    }
}
