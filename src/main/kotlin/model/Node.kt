package model

import model.State.*
import java.awt.Color
import java.awt.AlphaComposite
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D
import config.Configuration.nodeSize as n

/**
 * Represents the cell of game field.
 */
class Node(var x: Int, var y: Int, val color: model.Color) {

    /**
     * State of Node.
     */
    var state = ACTIVE

    /**
     * Rotate node clockwise.
     */
    fun rotate(a: Node) {
        if (x == a.x) {
            if (y > a.y) {
                x = a.x + (a.y - y)
                y = a.y
            } else if (y < a.y) {
                x = a.x + (a.y - y)
                y = a.y
            }
        } else if (x > a.x) {
            if (y > a.y) {
                val t = x
                x = a.x - (y - a.y)
                y = a.y + (t - a.x)
            } else if (y < a.y) {
                val t = x
                x = a.x + (a.y - y)
                y = a.y + (t - a.x)
            } else { // y == a.y
                y = a.y + (x - a.x)
                x = a.x
            }
        } else { // x < a.x
            if (y > a.y) {
                val t = x
                x = a.x - (y - a.y)
                y = a.y - (a.x - t)
            } else if (y < a.y) {
                val t = x
                x = a.x + (a.y - y)
                y = a.y - (a.x - t)
            } else { // y == a.y
                y = a.y - (a.x - x)
                x = a.x
            }
        }
    }

    /**
     * Move Node to left.
     */
    fun left() {
        x--
    }

    /**
     * Move Node to right.
     */
    fun right() {
        x++
    }

    /**
     * Move Node to down.
     */
    fun down() {
        y++
    }

    /**
     * Render node.
     */
    fun render(g: Graphics) {
        g as Graphics2D
        g.color = Color.BLACK
        g.fillRect(x * n, y * n, n, n)
        g.color = color.get()
        g.fill(RoundRectangle2D.Float(((x * n) + 1).toFloat(), ((y * n) + 1).toFloat(), 18.0F, 18.0F, 3.0F, 3.0F))
        g.setComposite(AlphaComposite.SrcAtop)
    }

    /**
     * Clones current Node.
     */
    fun clone() = Node(this.x, this.y, this.color)

}