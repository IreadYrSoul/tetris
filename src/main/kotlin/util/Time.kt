package util

/**
 * Time helper.
 */
object Time {

    /**
     * 1 second as microseconds.
     */
    const val second = 1000000000L

    /**
     * Get current nano time.
     */
    fun get() = System.nanoTime()
}