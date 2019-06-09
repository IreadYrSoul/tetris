package engine

import render.Display
import util.FrameRate
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferStrategy

import config.Configuration.displeyWidth as width
import config.Configuration.displayHeight as height


/**
 * @author Alexander Naumov.
 */
class Game : Runnable {

    val frameRate = FrameRate()
    val appSleep = 10L
    @Volatile
    var running = false

    lateinit var display:Display
    lateinit var bs:BufferStrategy

    lateinit var gameThread: Thread

    fun createAndShowGui() {
        display = Display()
        bs = display.bs
    }


    /**
     * the core loop of game.
     */
    private fun gameLoop(delta: Float) {
        renderFrame()
        sleep(appSleep)
    }

    private fun sleep(sleep: Long) {
        Thread.sleep(sleep)
    }

    private fun update() {

    }

    private fun input() {

    }

    private fun renderFrame() {
        do {
            do {
                var g:Graphics? = null
                try {
                    g = bs.drawGraphics
                    render(g)
                } finally {
                    if (g != null) {
                        g.dispose()
                    }
                }
            } while (bs.contentsRestored())
            bs.show()
        } while (bs.contentsLost())

    }

    private fun render(g:Graphics) {
        g.clearRect(0,0, width, height)
        g.color = Color.GREEN
        frameRate.calculate()
        g.drawString(frameRate.frameRate, 4, 15)
    }

    override fun run() {
        createAndShowGui()

        running = true
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

    fun start() {
        if (running) {
            return
        }
        running = true
        gameThread = Thread(this)
        gameThread.start()
    }

}