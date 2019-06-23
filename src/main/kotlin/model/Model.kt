package model

import input.Input
import java.awt.Graphics
import java.util.*
import model.State.NOT_ACTIVE

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
                        checkLine()
                        break
                    }
                }
            }
        }

        state = shape.active
        if (!state) {
            newShape()
            checkLine()
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
        var y = 0
        var count = 0
        for (i in array.size - 1 downTo 0) {
            if (!array[i].contains(null)) {
                y = i
                count++
            }
            if (count > 0) {
                break
            }
        }
        if (count > 0) {
            array[y].fill(null)
            for (i in y-1 downTo 0) {
                for (n in array[i]) {
                    if (n != null) {
                        n.y++
                        array[n.y][n.x] = n
                        array[n.y - 1][n.x] = null
                    }
                }
            }
            checkLine()
        }
    }

    fun render(g: Graphics) {
        shape.render(g)
        for (y in array.size - 1 downTo 0) {
            for (x in array[y].size - 1 downTo 0) {
                if (array[y][x] != null) {
                    array[y][x]!!.render(g)
                }
            }
        }
    }

    /**
     * Create new random instance of Shape.
     */
    private fun randomShape(): Shape {
        val types = Type.values()
        val colors = Color.values()
        val random = Random()
        return Shape(types[random.nextInt(types.size)], colors[random.nextInt(colors.size)])
    }
}

