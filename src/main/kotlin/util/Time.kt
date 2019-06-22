package util

object Time {

    const val SECOND = 1000000000L

    fun get() = System.nanoTime()
}