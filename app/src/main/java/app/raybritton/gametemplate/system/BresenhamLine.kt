package app.raybritton.gametemplate.system

/**
 * Construct a Bresenham algorithm.
 */
class BresenhamLine {
    /** The start and end of the line  */
    private var x1: Int = 0
    private var y1: Int = 0
    private var x2: Int = 0
    private var y2: Int = 0

    /** Used for calculation  */
    private var dx: Int = 0
    private var dy: Int = 0
    private var error: Int = 0
    private var x_inc: Int = 0
    private var y_inc: Int = 0
    /**
     * @return the current X coordinate
     */
    var x: Int = 0
        private set
    /**
     * @return the current Y coordinate
     */
    var y: Int = 0
        private set
    private var length: Int = 0
    private var count: Int = 0

    /**
     * Plot a line between (x1,y1) and (x2,y2). To step through the line use next().
     *
     * @return the length of the line (which will be 1 more than you are expecting).
     */
    fun plot(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        this.x1 = x1
        this.x2 = x2
        this.y1 = y1
        this.y2 = y2

        // compute horizontal and vertical deltas
        dx = x2 - x1
        dy = y2 - y1

        // test which direction the line is going in i.e. slope angle
        if (dx >= 0) {
            x_inc = 1
        } else {
            x_inc = -1
            dx = -dx // need absolute value
        }

        // test y component of slope

        if (dy >= 0) {
            y_inc = 1
        } else {
            y_inc = -1
            dy = -dy // need absolute value
        }

        x = x1
        y = y1

        if (dx > dy) {
            error = dx shr 1
        } else {
            error = dy shr 1
        }

        count = 0
        length = Math.max(dx, dy) + 1
        return length
    }

    /**
     * Get the next point in the line. You must not call next() if the
     * previous invocation of next() returned false.
     * Retrieve the X and Y coordinates of the line with getX() and getY().
     *
     * @return true if there is another point to come.
     */
    operator fun next(): Boolean {
        // now based on which delta is greater we can draw the line
        if (dx > dy) {
            // adjust the error term
            error += dy

            // test if error has overflowed
            if (error >= dx) {
                error -= dx

                // move to next line
                y += y_inc
            }

            // move to the next pixel
            x += x_inc
        } else {
            // adjust the error term
            error += dx

            // test if error overflowed
            if (error >= dy) {
                error -= dy

                // move to next line
                x += x_inc
            }

            // move to the next pixel
            y += y_inc
        }

        count++
        return count < length
    }

    companion object {

        /** General case algorithm  */
        private val bresenham = BresenhamLine()

        /**
         * Plot a line between (x1,y1) and (x2,y2). The results are placed in x[] and y[], which must be large enough.
         *
         * @return the length of the line or the length of x[]/y[], whichever is smaller
         */
        fun plot(x1: Int, y1: Int, x2: Int, y2: Int, x: IntArray, y: IntArray): Int {

            val length = Math.min(x.size, Math.min(y.size, bresenham.plot(x1, y1, x2, y2)))
            for (i in 0 until length) {
                x[i] = bresenham.x
                y[i] = bresenham.y
                bresenham.next()
            }

            return length
        }
    }
}