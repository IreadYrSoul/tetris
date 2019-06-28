package model

/**
 * Represents completed lines.
 */
object Lines {

    private var points = 0

    /**
     * Increments amount completed lines + 1
     */
    fun inc() {
        points++
    }

    /**
     * Get amount completed lines as String.
     */
    fun get():String {
        return when {
            (points in 0..10) -> "00$points"
            (points in 10..99) -> "0$points"
            else -> "$points"
        }
    }
}