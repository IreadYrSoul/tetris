package engine

import input.Input

import model.Model
import render.Display
import util.Stats
import util.Time
import java.awt.Graphics

import util.Time.second
import kotlin.system.exitProcess

import config.Configuration.height as h
import config.Configuration.width as w

/**
 * The game engine.
 */
class Game: Runnable {

    val updateRate = 60.0f
    val updateInterval = second / updateRate
    val idleTime = 1L
    val clearColor = 0xff000000.toInt()

    @Volatile
    var running = false

    lateinit var display: Display
    lateinit var g: Graphics
    lateinit var gameThread: Thread

    private val keys:Input
    private val model:Model
    private val stats:Stats

    init {
        keys = Input()
        model = Model(w, h, keys)
        stats = Stats(model.nextType, model.nextColor)
    }

    private fun createAndShowGui() {
        display = Display(keys)
        display.create(clearColor)

        display.exitMenuItem.addActionListener { e -> shutDown()}

        g = display.getGraphics()
    }

    /**
     * Update process.
     */
    private fun update() {
        model.update()
        stats.update(model.nextType, model.nextColor)
    }

    /**
     * Render process.
     */
    private fun render() {
        display.clear()
        //render stats.
        stats.render(g, model.lines)
        //render shape + model nodes.
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
            delta += (elapsedTime / updateInterval)
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
                Thread.sleep(idleTime)
            }
            if (counter >= second) {
                fps = 0
                upd = 0
                updl = 0
                counter = 0
            }
        }
    }

    /**
     * Shutdown game thread.
     */
    private fun shutDown() {
        if (Thread.currentThread() != gameThread) {
            running = false
            display.destroy()
            gameThread.join()
            exitProcess(0)
        }
    }

    /**
     * Start game thread.
     */
    fun start() {
        if (running) {
            return
        }
        running = true
        gameThread = Thread(this)
        gameThread.start()
    }
}