package app.raybritton.gametemplate.view

import android.graphics.PointF

class LinearLayout(parent: Layout, private val orientation: Orientation) : BaseLayout(parent) {
    enum class Orientation {
        VERTICAL, HORIZONTAL
    }

    override fun layout() {
        var pxOffset = 0f
        children.forEach { child ->
            when (orientation) {
                Orientation.VERTICAL -> {
                    child.x = when ((child.layoutParams as LinearLayoutParams).gravity) {
                        LinearLayoutParams.Gravity.TOP_LEFT -> child.layoutParams.leftMargin
                        LinearLayoutParams.Gravity.CENTER -> (contentWidth() * 0.5f) - (child.widthWithPadding() * 0.5f) + child.layoutParams.leftMargin - child.layoutParams.rightMargin
                        LinearLayoutParams.Gravity.BOTTOM_RIGHT -> contentWidth() - child.widthWithPadding() - child.layoutParams.rightMargin
                    }
                    child.y = pxOffset + child.layoutParams.topMargin
                    pxOffset += child.heightWithMargins()
                }
                Orientation.HORIZONTAL -> {
                    child.x = pxOffset + child.layoutParams.leftMargin
                    child.y = when ((child.layoutParams as LinearLayoutParams).gravity) {
                        LinearLayoutParams.Gravity.TOP_LEFT -> child.layoutParams.topMargin
                        LinearLayoutParams.Gravity.CENTER -> (contentHeight() * 0.5f) - (child.heightWithPadding() * 0.5f) + child.layoutParams.topMargin - child.layoutParams.bottomMargin
                        LinearLayoutParams.Gravity.BOTTOM_RIGHT -> contentHeight() - child.heightWithPadding() - child.layoutParams.bottomMargin
                    }
                    pxOffset += child.widthWithMargins()
                }
            }
        }
    }

    override fun getContentSize(maxW: Float, maxH: Float): PointF {
        return when (orientation) {
            Orientation.VERTICAL -> {
                val w = children.map { it.widthWithMargins() }.max() ?: 0f
                val h = children.filter { (it.layoutParams as LinearLayoutParams).weight == null }
                    .sumByDouble { it.heightWithMargins().toDouble() }
                    .toFloat()
                PointF(w, h)
            }
            Orientation.HORIZONTAL -> {
                val w = children.filter { (it.layoutParams as LinearLayoutParams).weight == null }
                    .sumByDouble { it.widthWithMargins().toDouble() }
                    .toFloat()
                val h = children.map { it.heightWithMargins() }.max() ?: 0f
                PointF(w, h)
            }
        }
    }

    override fun onMeasured(contentWidth: Float, contentHeight: Float) {
        super.onMeasured(contentWidth, contentHeight)
        var availableStretchSpace = when (orientation) {
            Orientation.VERTICAL -> contentHeight()
            Orientation.HORIZONTAL -> contentWidth()
        }
        children.filter { (it.layoutParams as LinearLayoutParams).weight == null }.forEach {
            when (orientation) {
                Orientation.VERTICAL -> availableStretchSpace -= it.heightWithMargins()
                Orientation.HORIZONTAL -> availableStretchSpace -= it.widthWithMargins()
            }
        }
        if (availableStretchSpace > 0) {
            val stretchableChildren = children.filter { (it.layoutParams as LinearLayoutParams).weight != null }
            val totalWeighting = stretchableChildren.map { (it.layoutParams as LinearLayoutParams).weight!! }.sum()
            stretchableChildren.forEach {
                val newSize =
                    ((it.layoutParams as LinearLayoutParams).weight!! / totalWeighting) * availableStretchSpace
                when (orientation) {
                    Orientation.VERTICAL -> it.h =
                        newSize - it.topPadding - it.bottomPadding - it.layoutParams.topMargin - it.layoutParams.bottomMargin
                    Orientation.HORIZONTAL -> it.w =
                        newSize - it.leftPadding - it.rightPadding - it.layoutParams.leftMargin - it.layoutParams.rightMargin
                }
            }
        }
    }


    class LinearLayoutParams(width: Size, height: Size) : LayoutParams(width, height) {
        var weight: Float? = null
        var gravity: Gravity = Gravity.TOP_LEFT
        enum class Gravity {
            TOP_LEFT, CENTER, BOTTOM_RIGHT
        }
    }
}