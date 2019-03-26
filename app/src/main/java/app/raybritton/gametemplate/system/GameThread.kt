package app.raybritton.gametemplate.system

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.*
import app.raybritton.gametemplate.StartupScene
import app.raybritton.gametemplate.TextTestScene
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.ext.safePeek
import timber.log.Timber
import java.util.*

class GameThread(context: Context, private val holder: SurfaceHolder) : Thread("gameThread"), View.OnTouchListener,
    Scene.SceneCallback {
    private var appStartTime: Long = 0
    var isRunning: Boolean = false
    private val scenes = Stack<Scene>()
    private var loadingScene: Scene? = null
    var width: Float = 0f
        private set
    var height: Float = 0f
        private set
    private val fpsPaint: Paint

    init {
        instance = this
        fpsPaint = Paint()
        fpsPaint.textSize = Dimen.dpToPx(30)
        fpsPaint.color = Color.RED
        fpsPaint.textAlign = Paint.Align.RIGHT
        fpsPaint.style = Paint.Style.FILL
    }

    fun update(currentTime: Long, deltaTime: Float) {
        scenes.safePeek()?.update(currentTime, deltaTime)
    }

    private fun render(c: Canvas, currentTime: Long) {
        scenes.safePeek()?.render(c, currentTime)
    }

    fun resize(width: Int, height: Int) {
        this.width = width.toFloat()
        this.height = height.toFloat()
        scenes.safePeek()?.resize(this.width, this.height)
    }

    override fun run() {
        var lastFps = ""
        var nextLogTime: Long = 0
        var last = System.nanoTime()
        var now: Long
        var lastTick: Long = System.currentTimeMillis()
        var frames = 0
        var ticks = 0
        var delta: Float
        var unprocessed = 0.0
        while (isRunning) {
            var c: Canvas? = null
            try {
                c = holder.lockCanvas(null)
                if (c == null) break
                synchronized(holder) {
                    now = System.nanoTime()
                    var nowMs = System.currentTimeMillis()
                    unprocessed += (now - last) / 16666666.66667
                    last = now
                    while (unprocessed >= 1) {
                        unprocessed--
                        ticks++
                        nowMs = System.currentTimeMillis()
                        delta = (nowMs - lastTick).toFloat()
                        lastTick = nowMs

                        update(nowMs - appStartTime, delta)
                    }

                    frames++
                    render(c, nowMs)
                    c.drawText(lastFps, (width - 20), 60f, fpsPaint)

                    val frameDiff = System.currentTimeMillis() - nowMs

                    try {
                        Thread.sleep(Math.min(1, frameDiff))
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    if (nextLogTime < nowMs) {
                        lastFps = "F: $frames  U: $ticks"
                        Timber.d(lastFps)
                        nextLogTime = nowMs + 1000
                        frames = 0
                        ticks = 0
                    }
                }
            } finally {
                if (c != null) {
                    holder.unlockCanvasAndPost(c)
                }
            }
        }
    }

    @Synchronized
    override fun start() {
        if (!isAlive) {
            super.start()
        }
        appStartTime = System.currentTimeMillis()
        loadScene(StartupScene(this)) {
            //all scenes closed?
        }
    }

    private val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
        override fun onShowPress(event: MotionEvent) {
            scenes.peek().showPress(event.x, event.y)
        }

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            scenes.peek().handleTap(event.x, event.y)
            return true
        }

        override fun onDown(event: MotionEvent) = true

        override fun onFling(event1: MotionEvent, event2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            scenes.peek().handleFling(event1.x, event1.y, event2.x, event2.y)
            return true
        }

        override fun onScroll(event1: MotionEvent, event2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            scenes.peek().handleDrag(event1.x, event1.y, event2.x, event2.y)
            return true
        }

        override fun onLongPress(event: MotionEvent) {
            scenes.peek().handleLongPress(event.x, event.y)
        }
    })

    private val scaleDetector =
        ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scenes.peek().handleScale(detector.scaleFactor)
                return true
            }
        })

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_OUTSIDE || event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
            scenes.peek().handlePointerUp(event.x, event.y)
        }
        return true
    }

    override fun onSceneReady(scene: Scene) {
        Timber.d("%s loaded", scene.name)
        if (scene == loadingScene) {
            scenes.push(loadingScene)
            loadingScene = null
        }
    }

    override fun loadScene(scene: Scene, state: Map<String, Any>, callback: (Map<String, Any>) -> Unit) {
        Timber.d("Load scene %s from %s state", scene.name, scenes.safePeek()?.name)
        scene.resultCallback = callback
        loadingScene = scene
        Thread(Runnable { loadingScene?.load(state) }).start()
    }

    override fun unloadScene(state: Map<String, Any>) {
        val resultCallback = scenes.peek().resultCallback
        scenes.pop()
        resultCallback?.invoke(state)
    }

    companion object {
        lateinit var instance: GameThread
    }
}
