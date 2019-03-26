package app.raybritton.gametemplate.view

import android.graphics.PointF
import java.lang.IllegalStateException

class FrameLayout(parent: Layout) : BaseLayout(parent) {
    override fun layout() {
        children.forEach { child ->
            if (child.layoutParams !is FrameLayoutParams) {
                throw IllegalStateException("child at ${child.x},${child.y} has invalid params, must be FrameLayoutParams")
            }
            (child.layoutParams as FrameLayoutParams).let { params ->
                child.x =
                    (this.contentWidth() * params.parentAnchor.xPerc) - (child.widthWithPadding() * params.childAnchor.xPerc) + params.calcXMargin()
                child.y =
                    (this.contentHeight() * params.parentAnchor.yPerc) - (child.heightWithPadding() * params.childAnchor.yPerc) + params.calcYMargin()
            }
        }
    }

    override fun getContentSize(maxW: Float, maxH: Float): PointF {
        if (children.isEmpty()) return PointF(0f, 0f)
        val w = children.map { it.widthWithMargins() }.max() ?: 0f
        val h = children.map { it.heightWithMargins() }.max() ?: 0f
        return PointF(w, h)
    }

    class FrameLayoutParams(width: Size, height: Size) : LayoutParams(width, height) {
        var parentAnchor: Anchor = Anchor.TOP_LEFT
        var childAnchor: Anchor = Anchor.TOP_LEFT

        fun calcXMargin(): Float {
            return when {
                parentAnchor.xPerc < 0.5 -> leftMargin
                parentAnchor.xPerc > 0.5 -> -rightMargin
                else -> leftMargin - rightMargin
            }
        }

        fun calcYMargin(): Float {
            return when {
                parentAnchor.yPerc < 0.5 -> topMargin
                parentAnchor.yPerc > 0.5 -> -bottomMargin
                else -> topMargin - bottomMargin
            }
        }

        enum class Anchor(val xPerc: Float, val yPerc: Float) {
            TOP_LEFT(0f, 0f), TOP(0.5f, 0f), TOP_RIGHT(1f, 0f),
            LEFT(0f, 0.5f), CENTER(0.5f, 0.5f), RIGHT(1f, 0.5f),
            BOTTOM_LEFT(0f, 1f), BOTTOM(0.5f, 1f), BOTTOM_RIGHT(1f, 1f);
        }
    }
}