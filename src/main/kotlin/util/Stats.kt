package util

import model.*
import model.Type.*
import java.awt.Font
import java.awt.Graphics

import config.Configuration.statDisplayHeight as sH
import config.Configuration.gameDisplayHeight as gH
import config.Configuration.displayWidth as dW

/**
 * Represents stats data (next Shape and completed Lines).
 */
class Stats (var type: Type, var color: Color) {

    /**
     * Frame rate.
     */
    private val frameRate = FrameRate()

    /**
     * Body of next Shape.
     */
    private var nextShape:Array<Node?>

    /**
     * Base X coordinate for stat Node.
     */
    val x = 120

    /**
     * Base Y coordinate for stat Node.
     */
    val y = 10

    init {
       nextShape = fill(type, color)
    }

    /**
     * Update frame rate (FPS).
     */
    fun update(t: Type, c: Color) {
        frameRate.calculate()
        if (type != t || color != c) {
            nextShape = fill(t, c)
        }

    }

    /**
     * Generate Shape body for static rendering of stats panel.
     */
    private fun fill(t: Type, c: Color): Array<Node?> {
        val body = arrayListOf<Node>()
        when(t) {
            I -> {
                body.add(Node(x - 10, y + 10, c, true))
                body.add(Node(x + 10, y + 10, c, true))
                body.add(Node(x + 30, y + 10, c, true))
                body.add(Node(x + 50, y + 10, c, true))
            }
            O -> {
                body.add(Node(x + 10, y, c, true))
                body.add(Node(x + 10, y + 20, c, true))
                body.add(Node(x + 30, y, c, true))
                body.add(Node(x + 30, y + 20, c, true))
            }
            T -> {
                body.add(Node(x, y, c, true))
                body.add(Node(x + 20, y, c, true))
                body.add(Node(x + 40, y, c, true))
                body.add(Node(x + 20, y + 20, c, true))
            }
            Z -> {
                body.add(Node(x, y, c, true))
                body.add(Node(x + 20, y, c, true))
                body.add(Node(x + 20, y + 20, c, true))
                body.add(Node(x + 40, y + 20, c, true))
            }
            S -> {
                body.add(Node(x + 20, y, c, true))
                body.add(Node(x + 40, y, c, true))
                body.add(Node(x, y + 20, c, true))
                body.add(Node(x + 20, y + 20, c, true))
            }
            J -> {
                body.add(Node(x, y, c, true))
                body.add(Node(x, y + 20, c, true))
                body.add(Node(x + 20, y + 20, c, true))
                body.add(Node(x + 40, y + 20, c, true))
            }
            L -> {
                body.add(Node(x + 40, y, c, true))
                body.add(Node(x, y + 20, c, true))
                body.add(Node(x + 20, y + 20, c, true))
                body.add(Node(x + 40, y + 20, c, true))
            }
        }
        return body.toTypedArray()
    }

    /**
     * Render stats.
     */
    fun render(g: Graphics, l: Lines) {
        g.color = java.awt.Color.WHITE
        g.fillRect(0, 0, dW, sH + gH)
        g.color = java.awt.Color.BLACK
        g.fillRect(0, 0, (dW / 2) - 1, sH - 2)
        g.fillRect((dW / 2) + 1, 0, (dW / 2) - 1, sH - 2)
        g.fillRect(0,  sH, dW, gH)
        g.color = java.awt.Color.WHITE
        g.font = Font("Arial", Font.BOLD, 20)
        g.drawString("LINES", 18, 25)
        g.drawString(l.get(), 30, 50)
        nextShape.forEach { it!!.render(g) }
    }
}