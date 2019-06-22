package model

import model.State.*
import java.awt.Color
import java.awt.AlphaComposite
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.RoundRectangle2D

/**
 * Represents the cell of game field.
 */
class Node(var x: Int, var y: Int, val color: model.Color) {

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
     * Move node to left.
     */
    fun left() {
        x--
    }

    /**
     * Move node to right.
     */
    fun right() {
        x++
    }

    /**
     * Move node to down.
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
        g.fillRect(x * 20, y * 20, 20, 20)
        g.color = color.get()
        g.fill(RoundRectangle2D.Float(((x * 20) + 1).toFloat(), ((y * 20) + 1).toFloat(), 18.0F, 18.0F, 3.0F, 3.0F))
        g.setComposite(AlphaComposite.SrcAtop)
    }

}