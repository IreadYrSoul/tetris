package engine

import graphics.Display
import util.FrameRate


/**
 * @author Alexander Naumov.
 */
class Game : Runnable {

    val frameRate = FrameRate()

    val appSleep = 10L

    var running = false

    lateinit var gameThread: Thread

    lateinit var display: Display

    fun createAndShowGui() {
        display = Display()
        gameThread = Thread(this)
    }


    /**
     * the core loop of game.
     */
    private fun gameLoop(delta: Float) {
        input()
        update()
        render()
        sleep(appSleep)
    }

    private fun sleep(sleep: Long) {
        Thread.sleep(sleep)
    }

    private fun update() {

    }

    private fun input() {

    }

    private fun render() {
        frameRate.calculate()
    }

    override fun run() {
        running = true
        initialize()
        var currTime = System.nanoTime()
        var lastTime = currTime
        var nsPerFrame: Long
        while (running) {
            currTime = System.nanoTime()
            nsPerFrame = currTime - lastTime
            gameLoop((nsPerFrame / 1.0E9).toFloat())
            lastTime = currTime
        }
    }

    private fun shutDown() {
        if (Thread.currentThread() != gameThread) {
            running = false
            gameThread.join()
            System.exit(0)
        }
    }

    private fun initialize() {

    }

    fun start() {

    }
}