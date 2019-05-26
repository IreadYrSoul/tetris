package util

/**
 * Measure the application performance and
 * verify that the windows is redrawing.
 */
class FrameRate {

    var frameRate:String
    private var lastTime:Long
    private var delta:Long
    private var frameCount:Int

    init {
        frameCount = 0
        delta = 0
        lastTime = System.currentTimeMillis()
        frameRate = "FPS 0"
    }

    /**
     * Calculated the FPS.
     * Called once for every rendered frame.
     */
    fun calculate() {
        var current = System.currentTimeMillis()
        delta += current - lastTime
        lastTime = current
        frameCount++
        if (delta > 1000) {
            delta -= 1000
            frameRate = String.format("FPS $frameCount")
            frameCount = 0
        }
    }
}