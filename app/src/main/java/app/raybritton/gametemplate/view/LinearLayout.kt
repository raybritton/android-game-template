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
                    child.x = child.layoutParams.leftMargin
                    child.y = pxOffset + child.layoutParams.topMargin
                    pxOffset += child.heightWithMargins()
                }
                Orientation.HORIZONTAL -> {
                    child.x = pxOffset + child.layoutParams.leftMargin
                    child.y = child.layoutParams.topMargin
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
            Orientation.VERTICAL -> contentHeight
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
                    Orientation.VERTICAL -> it.h = newSize
                    Orientation.HORIZONTAL -> it.w = newSize
                }
            }
        }
    }


    class LinearLayoutParams(width: Size, height: Size) : LayoutParams(width, height) {
        var weight: Float? = null
    }
}