package engine

import input.Input
import model.Shape
import model.Model
import render.Display
import util.FrameRate
import util.Time
import java.awt.Color
import java.awt.Graphics
import java.util.*

import config.Configuration.displeyWidth as width
import config.Configuration.displayHeight as height
import config.Configuration.height as h
import config.Configuration.width as w

/**
 * @author Alexander Naumov.
 */
class Game: Runnable {

    val UPDATE_RATE = 60.0f
    val UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE
    val IDLE_TIME = 1L
    val CLEAR_COLOR = 0xff000000.toInt()

    val frameRate = FrameRate()

    @Volatile
    var running = false

    lateinit var display: Display
    lateinit var g: Graphics
    lateinit var gameThread: Thread

    val keys = Input()
    val model = Model(w, h, keys)

    fun createAndShowGui() {
        display = Display(keys)
        display.create(CLEAR_COLOR)
        g = display.getGraphics()
    }


    private fun update() {
        model.update()
    }

    private fun input() {

    }


    private fun render() {
        display.clear()

        g.color = Color.GREEN
        frameRate.calculate()
        g.drawString(frameRate.frameRate, 4, 15)


        model.render(g)

        display.swapBuffers()
    }

    /**
     * the core loop of game.
     */
    override fun run() {
        createAndShowGui()
        var fps = 0
        var upd = 0
        var updl = 0
        var counter = 0L
        var lastTime = Time.get()
        var delta = 0.0F
        while (running) {
            var now = Time.get()
            var elapsedTime = now - lastTime
            lastTime = now
            counter += elapsedTime
            var render = false
            delta += (elapsedTime / UPDATE_INTERVAL)
            while (delta > 1) {
                update()
                upd++
                delta--
                if (render) {
                    updl++
                } else {
                    render = true
                }
            }
            if (render) {
                render()
                fps++
            } else {
                Thread.sleep(IDLE_TIME)
            }
            if (counter >= Time.SECOND) {
                fps = 0
                upd = 0
                updl = 0
                counter = 0
            }
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