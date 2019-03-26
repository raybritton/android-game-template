package app.raybritton.gametemplate.android

import android.app.Application
import android.util.TypedValue
import app.raybritton.gametemplate.BuildConfig
import timber.log.Timber

class GameApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Dimen.dpToPx = { dp ->
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}