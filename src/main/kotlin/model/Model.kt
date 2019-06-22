package model

import input.Input
import java.awt.Graphics
import java.util.*
import model.State.NOT_ACTIVE
import config.Configuration.width as w
import config.Configuration.height as h

/**
 * Represents model of game field.
 */
class Model(w: Int, h: Int, val input: Input) {

    val array: Array<Array<Node?>> = Array(h) { Array<Node?>(w) { null } }

    var shape: Shape

    var state = true
    var nodes = 0

    init {
        shape = randomShape()
    }

    fun update() {
        shape.update(input)

        if (nodes != 0) {
            for (n in shape.nodes) {
                if (n.y < array.size - 1) {
                    if (array[n.y + 1][n.x] != null) {
                        shape.active = false
                        newShape()
                        break
                    }
                }
            }
        }

        state = shape.active
        if (!state) {
            newShape()
        }
    }

    fun newShape() {
        for (n in shape.nodes) {
            n.state = NOT_ACTIVE
            array[n.y][n.x] = n
            nodes++
        }
        shape = randomShape()
    }

    private fun checkLine() {
        for (a in array) {

        }
    }

    fun render(g: Graphics) {
        shape.render(g)
        for (i in array) {
            for (n in array[array.indexOf(i)]) {
                if (n != null) {
                    n.render(g)
                }
            }
        }
    }

    /**
     * Create new random instance of Shape.
     */
    private fun randomShape(): Shape {
        val types = Type.values()
        val random = Random()
        return Shape(types[random.nextInt(types.size)])
    }
}

