package app.raybritton.gametemplate.system

abstract class BaseDirection(private val ordinal: Int) {
    @Suppress("LeakingThis") //this is fine, the results of values should be static
    val degrees = ((360 / values().size.toFloat()) * ordinal)

    fun opposite() = this.rotateBy(180f)
    fun rotateBy(degrees: Float): BaseDirection {
        val turns = degrees / (360f / values().size)
        var slot = (this.ordinal + turns).toInt() % values().size
        if (slot < 0) {
            slot += values().size
        }
        return values()[slot]
    }

    abstract fun values(): Array<BaseDirection>

    fun degreesTo(direction: BaseDirection) = direction.degrees - this.degrees

    companion object {
        fun nearestTo(degrees: Float, values: Array<BaseDirection>): BaseDirection {
            var degree = degrees
            while (degree < 0) {
                degree += 360
            }
            val idx = if (degree > (360 - ((360 / (values.size * 2)) + 1))) {
                0
            } else {
                ((degree / (360 / values.size)) % values.size).toInt()
            }
            return values[idx]
        }
    }
}

sealed class Direction(ordinal: Int) : BaseDirection(ordinal) {
    class North : Direction(0)
    class East : Direction(1)
    class South : Direction(2)
    class West : Direction(3)

    override fun values() = arrayOf<BaseDirection>(N, E, S, W)

    companion object {
        val N = North()
        val E = East()
        val S = South()
        val W = West()

        fun values() = N.values()

        fun nearestTo(degrees: Float) = BaseDirection.nearestTo(degrees, N.values())
    }
}