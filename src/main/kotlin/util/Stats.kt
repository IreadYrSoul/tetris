package util

import model.*
import model.Type.*
import java.awt.Color
import java.awt.Font
import java.awt.Graphics

import config.Configuration.statDisplayHeight as sH
import config.Configuration.gameDisplayHeight as gH
import config.Configuration.displayWidth as dW

/**
 * Represents stats data (next Shape and completed Lines).
 */
class Stats(var type: Type, var color: NodeColor) {

    /**
     * Frame rate.
     */
    private val frameRate = FrameRate()

    /**
     * Body of next Shape.
     */
    private var nextShape: Array<Node?>

    /**
     * Base X coordinate for stat Node.
     */
    private val x = 120

    /**
     * Base Y coordinate for stat Node.
     */
    private val y = 10

    init {
        nextShape = fill(type, color)
    }

    /**
     * Update frame rate (FPS).
     */
    fun update(t: Type, c: NodeColor) {
        frameRate.calculate()
        if (type != t || color != c) {
            nextShape = fill(t, c)
        }
    }

    /**
     * Generate Shape body for static rendering of stats panel.
     */
    private fun fill(t: Type, c: NodeColor): Array<Node?> {
        return when (t) {
            I -> arrayOf(Node(x - 10, y + 10, c, true),
                    Node(x + 10, y + 10, c, true),
                    Node(x + 30, y + 10, c, true),
                    Node(x + 50, y + 10, c, true))
            O -> arrayOf(Node(x + 10, y, c, true),
                    Node(x + 10, y + 20, c, true),
                    Node(x + 30, y, c, true),
                    Node(x + 30, y + 20, c, true))
            T -> arrayOf(Node(x, y, c, true),
                    Node(x + 20, y, c, true),
                    Node(x + 40, y, c, true),
                    Node(x + 20, y + 20, c, true))
            Z -> arrayOf(Node(x, y, c, true),
                    Node(x + 20, y, c, true),
                    Node(x + 20, y + 20, c, true),
                    Node(x + 40, y + 20, c, true))
            S -> arrayOf(Node(x + 20, y, c, true),
                    Node(x + 40, y, c, true),
                    Node(x, y + 20, c, true),
                    Node(x + 20, y + 20, c, true))
            J -> arrayOf(Node(x, y, c, true),
                    Node(x, y + 20, c, true),
                    Node(x + 20, y + 20, c, true),
                    Node(x + 40, y + 20, c, true))
            L -> arrayOf(Node(x + 40, y, c, true),
                    Node(x, y + 20, c, true),
                    Node(x + 20, y + 20, c, true),
                    Node(x + 40, y + 20, c, true))
        }
    }

    /**
     * Render stats.
     */
    fun render(g: Graphics, l: Lines, lvl: Level) {
        g.color = Color.WHITE
        g.fillRect(0, 0, dW, sH + gH)
        g.color = Color.BLACK
        g.fillRect(0, 0, (dW / 2) - 1, sH - 2)
        g.fillRect((dW / 2) + 1, 0, (dW / 2) - 1, sH - 2)
        g.fillRect(0, sH, dW, gH)
        g.color = Color.WHITE
        g.font = Font("Arial", Font.BOLD, 20)
        g.drawString("LINES", 20, 25)
        g.drawString(l.get(), 32, 50)
        g.color = Color(126, 214, 223)
        g.font = Font("Arial", Font.BOLD, 10)
        g.drawString(lvl.code, 30, 70)
        g.color = Color(106, 176, 76)
        g.drawString(frameRate.rate, 132, 70)
        nextShape.forEach { it!!.render(g) }
    }
}