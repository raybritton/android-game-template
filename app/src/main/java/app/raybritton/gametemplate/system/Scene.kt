package app.raybritton.gametemplate.system

import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.view.BaseLayout
import app.raybritton.gametemplate.view.Layout
import app.raybritton.gametemplate.view.View

abstract class Scene(var game: GameThread) : Layout {
    private var systemCallback: SceneCallback = game
    var resultCallback: ((Map<String, Any>) -> Unit)? = null
    protected var width = game.width
    protected var height = game.height
    override val isRootLayout = true
    protected lateinit var layout: BaseLayout

    abstract val name: String

    interface SceneCallback {
        fun onSceneReady(scene: Scene)

        fun loadScene(scene: Scene, state: Map<String, Any> = mapOf(), callback: (Map<String, Any>) -> Unit)

        fun unloadScene(state: Map<String, Any>)
    }

    override fun children() = listOf(layout)
    override fun contentWidth() = width
    override fun contentHeight() = height
    override fun sizingWidth() = width
    override fun sizingHeight() = height

    fun load(data: Map<String, Any>) {
        loadScene(data)
        resize(game.width, game.height)
        systemCallback.onSceneReady(this)
    }

    protected abstract fun loadScene(data: Map<String, Any>)

    open fun update(currentTime: Long, deltaTime: Float) {
        layout.onUpdate(currentTime, deltaTime)
    }

    open fun render(canvas: Canvas, currentTime: Long) {
        layout.render(canvas)
    }

    fun buildPaint(color: Int, style: Paint.Style, strokeWidth: Int): Paint {
        val paint = Paint()
        paint.color = color
        paint.style = style
        paint.strokeWidth = Dimen.dpToPx(strokeWidth)
        return paint
    }

    fun buildTextPaint(color: Int, textSize: Int, align: Paint.Align): TextPaint {
        val paint = TextPaint()
        paint.color = color
        paint.textSize = Dimen.dpToPx(textSize)
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.textAlign = align
        return paint
    }

    /**
     * This may be called at any time
     */
    open fun resize(width: Float, height: Float) {
        this.width = width
        this.height = height
        layout.measure(this)
        layout.layout()
    }

    open fun showPress(x: Float, y: Float) {}
    open fun handleTap(x: Float, y: Float) {}
    open fun handleFling(x1: Float, y1: Float, x2: Float, y2: Float) {}
    open fun handleDrag(originX: Float, originY: Float, currentX: Float, currentY: Float) {}
    open fun handleLongPress(x: Float, y: Float) {}
    open fun handleScale(factor: Float) {}
    open fun handlePointerUp(x: Float, y: Float) {}
}
