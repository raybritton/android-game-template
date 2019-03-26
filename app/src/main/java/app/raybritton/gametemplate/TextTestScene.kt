package app.raybritton.gametemplate

import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import app.raybritton.gametemplate.android.Dimen
import app.raybritton.gametemplate.system.GameThread
import app.raybritton.gametemplate.system.Scene
import app.raybritton.gametemplate.view.*
import app.raybritton.gametemplate.view.FrameLayout.FrameLayoutParams.*

class TextTestScene(gameThread: GameThread) : Scene(gameThread) {
    override val name: String = "Text test"

    init {
        layout = frameLayout {
            textView("TL") {
                frameLayoutParams(parentAnchor = Anchor.TOP_LEFT)
            }
            textView("T") {
                frameLayoutParams(parentAnchor = Anchor.TOP)
            }
            textView("TR") {
                frameLayoutParams(parentAnchor = Anchor.TOP_RIGHT)
            }
            textView("ML") {
                frameLayoutParams(parentAnchor = Anchor.LEFT)
            }
            textView("M") {
                frameLayoutParams(parentAnchor = Anchor.CENTER)
            }
            textView("MR") {
                frameLayoutParams(parentAnchor = Anchor.RIGHT)
            }
            textView("BL") {
                frameLayoutParams(parentAnchor = Anchor.BOTTOM_LEFT)
            }
            textView("B") {
                frameLayoutParams(parentAnchor = Anchor.BOTTOM)
            }
            textView("BR") {
                frameLayoutParams(parentAnchor = Anchor.BOTTOM_RIGHT)
            }
            textView("jÉ") {
                frameLayoutParams(Size.WRAP_CONTENT, Size.WRAP_CONTENT, Anchor.TOP) {
                    topMargin = Dimen.dpToPx(50)
                }
                leftPadding = Dimen.dpToPx(50)
                rightPadding = leftPadding
                topPadding = leftPadding
                bottomPadding = leftPadding
                alignment = Layout.Alignment.ALIGN_CENTER
            }
            textView("left") {
                frameLayoutParams(Size.MATCH_PARENT, Size.WRAP_CONTENT, Anchor.CENTER) {
                    topMargin = Dimen.dpToPx(60)
                    leftMargin = Dimen.dpToPx(32)
                }
                alignment = Layout.Alignment.ALIGN_CENTER
            }
            textView("right") {
                frameLayoutParams(Size.MATCH_PARENT, Size.WRAP_CONTENT, Anchor.CENTER) {
                    topMargin = Dimen.dpToPx(100)
                    rightMargin = Dimen.dpToPx(32)
                }
                alignment = Layout.Alignment.ALIGN_CENTER
            }
            textView("both") {
                frameLayoutParams(Size.MATCH_PARENT, Size.WRAP_CONTENT, Anchor.CENTER) {
                    topMargin = Dimen.dpToPx(140)
                    leftMargin = Dimen.dpToPx(32)
                    rightMargin = Dimen.dpToPx(32)
                }
                background.style = Paint.Style.STROKE
                background.color = Color.MAGENTA
                alignment = Layout.Alignment.ALIGN_CENTER
            }
            textView("[TEST]") {
                frameLayoutParams(parentAnchor = Anchor.CENTER) {
                    bottomMargin = Dimen.dpToPx(40)
                }
            }

            textView("B - T") {
                frameLayoutParams(parentAnchor = Anchor.BOTTOM) {
                    bottomMargin = Dimen.dpToPx(32)
                }
            }

            textView("X") {
                frameLayoutParams(parentAnchor = Anchor.CENTER, childAnchor = Anchor.RIGHT) {
                    rightMargin = Dimen.dpToPx(300)
                    bottomMargin = Dimen.dpToPx(300)
                }
                bottomPadding = Dimen.dpToPx(4)
                topPadding = Dimen.dpToPx(8)
                leftPadding = Dimen.dpToPx(12)
                rightPadding = Dimen.dpToPx(16)
            }

            frameLayout {
                frameLayoutParams(parentAnchor = Anchor.BOTTOM_LEFT) {
                    leftMargin = Dimen.dpToPx(100)
                    bottomMargin = Dimen.dpToPx(100)
                }
                textView("BIGGER") {
                    frameLayoutParams(parentAnchor = Anchor.TOP_LEFT) {
                        bottomMargin = Dimen.dpToPx(32)
                    }
                }
                textView("SMALL") {
                    frameLayoutParams(parentAnchor = Anchor.BOTTOM_RIGHT) {
                    }
                }
            }

            frameLayout {
                frameLayoutParams(Size.EXACT(70f), Size.EXACT(70f), parentAnchor = Anchor.BOTTOM_RIGHT) {
                    rightMargin = Dimen.dpToPx(100)
                    bottomMargin = Dimen.dpToPx(100)
                }
                background.color = Color.WHITE
                textView("HERE") {
                    frameLayoutParams(Size.EXACT(100f), Size.EXACT(100f), parentAnchor = Anchor.TOP_LEFT)
                    background.color = Color.MAGENTA
                }
            }

            linearLayout(LinearLayout.Orientation.VERTICAL) {
                frameLayoutParams(Size.EXACT(300f), Size.EXACT(300f), parentAnchor = Anchor.TOP_RIGHT) {
                    topMargin = Dimen.dpToPx(100)
                    rightMargin = Dimen.dpToPx(100)
                }
                textView("LINE 1") {
                    linearLayoutParams { }
                }
                textView("LINE 2") {
                    linearLayoutParams {
                        topMargin = Dimen.dpToPx(4)
                    }
                }
                textView("LINE 3") {
                    linearLayoutParams { }
                }
            }

            linearLayout(LinearLayout.Orientation.HORIZONTAL) {
                frameLayoutParams(Size.WRAP_CONTENT, Size.WRAP_CONTENT, parentAnchor = Anchor.TOP_LEFT) {
                    topMargin = Dimen.dpToPx(100)
                    leftMargin = Dimen.dpToPx(100)
                }
                textView("A") {
                    linearLayoutParams {
                        leftMargin = Dimen.dpToPx(8)
                    }
                }
                textView("B") {
                    linearLayoutParams {
                        leftMargin = Dimen.dpToPx(8)
                        rightMargin = Dimen.dpToPx(8)
                    }
                }
                textView("C") {
                    linearLayoutParams { }
                }
            }

            linearLayout(LinearLayout.Orientation.VERTICAL) {
                leftPadding = Dimen.dpToPx(8)
                topPadding = leftPadding
                bottomPadding = leftPadding
                rightPadding = leftPadding
                frameLayoutParams(parentAnchor = Anchor.TOP_RIGHT) {
                    rightMargin = Dimen.dpToPx(16)
                    topMargin = Dimen.dpToPx(60)
                }
                textView("A") {
                    linearLayoutParams {  }
                }
                textView("B") {
                    linearLayoutParams {
                        rightMargin = Dimen.dpToPx(12)
                    }
                }
                textView("C") {
                    linearLayoutParams { }
                    leftPadding = Dimen.dpToPx(8)
                }
            }

            linearLayout(LinearLayout.Orientation.HORIZONTAL) {
                frameLayoutParams(width = Size.EXACT(Dimen.dpToPx(150)), parentAnchor = Anchor.LEFT) {
                    leftMargin = Dimen.dpToPx(60)
                    bottomMargin = Dimen.dpToPx(30)
                }
                textView(" ") {
                    linearLayoutParams {
                        weight = 1f
                    }
                    background.color = Color.BLUE
                }
                textView((" ")) {
                    linearLayoutParams(width = Size.EXACT(Dimen.dpToPx(50))) {}
                    background.color = Color.RED
                }
            }

            linearLayout(LinearLayout.Orientation.HORIZONTAL) {
                frameLayoutParams(width = Size.EXACT(Dimen.dpToPx(150)), parentAnchor = Anchor.LEFT) {
                    leftMargin = Dimen.dpToPx(60)
                    bottomMargin = Dimen.dpToPx(60)
                }
                textView(" ") {
                    linearLayoutParams {
                        weight = 0.49f
                    }
                    background.color = Color.GREEN
                }
                textView((" ")) {
                    linearLayoutParams {
                        weight = 0.51f
                    }
                    background.color = Color.RED
                }
            }

            linearLayout(LinearLayout.Orientation.VERTICAL) {
                frameLayoutParams(width = Size.EXACT(Dimen.dpToPx(20)), height = Size.EXACT(Dimen.dpToPx(54)), parentAnchor = Anchor.LEFT) {
                    leftMargin = Dimen.dpToPx(20)
                    bottomMargin = Dimen.dpToPx(60)
                }
                textView(" ") {
                    linearLayoutParams(width = Size.MATCH_PARENT) {
                        weight = 1f
                    }
                    background.color = Color.GREEN
                }
                textView((" ")) {
                    linearLayoutParams(width = Size.MATCH_PARENT, height = Size.EXACT(Dimen.dpToPx(50))) {}
                    background.color = Color.RED
                }
            }

            linearLayout(LinearLayout.Orientation.VERTICAL) {
                leftPadding = Dimen.dpToPx(8)
                topPadding = leftPadding
                rightPadding = leftPadding
                bottomPadding = leftPadding
                frameLayoutParams(parentAnchor = Anchor.TOP) {
                    rightMargin = Dimen.dpToPx(140)
                    topMargin = Dimen.dpToPx(30)
                }
                button("Click me", {
                    if (layout.background.color == Color.BLUE) {
                        layout.background.color = Color.BLACK
                    } else {
                        layout.background.color = Color.BLUE
                    }
                }) {
                    linearLayoutParams(Size.WRAP_CONTENT, Size.WRAP_CONTENT) {}
                }
                toggleButton("Toggle me", {}) {
                    linearLayoutParams(Size.WRAP_CONTENT, Size.WRAP_CONTENT) {}
                }
            }
        }
    }

    override fun handleTap(x: Float, y: Float) {
        layout.handleTap(x, y)
    }

    override fun loadScene(data: Map<String, Any>) {

    }
}