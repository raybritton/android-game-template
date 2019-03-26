package app.raybritton.gametemplate.ext

import java.util.*

fun <T> Stack<T>.safePeek(): T? {
    if (isEmpty()) return null
    return peek()
}