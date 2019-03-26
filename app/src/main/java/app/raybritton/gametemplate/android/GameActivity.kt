package app.raybritton.gametemplate.android

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.*
import app.raybritton.gametemplate.system.GameThread
import timber.log.Timber

class GameActivity : Activity(), SurfaceHolder.Callback {

    private lateinit var view: SurfaceView
    private var game: GameThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED and WindowManager.LayoutParams.FLAG_FULLSCREEN and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        view = SurfaceView(this)
        setContentView(view)
        view.holder.addCallback(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Timber.i("Surface created")
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Timber.i("Surface changed (%sx%s)", width, height)
        if (game == null) {
            game = GameThread(this, holder)
            game!!.isRunning = true
            game!!.start()
            view.setOnTouchListener(game)
        }
        game!!.resize(width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Timber.i("Surface destroyed")
        killGame()
    }

    private fun killGame() {
        var retry = true
        game?.isRunning = false
        while (retry) {
            try {
                game?.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }
}
