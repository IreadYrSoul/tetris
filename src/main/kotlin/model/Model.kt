package model

import input.Input
import java.awt.Graphics
import java.util.*
import model.State.NOT_ACTIVE
import config.Configuration.width as w

/**
 * Represents model of game field.
 */
class Model(w: Int, h: Int, val input: Input) {

    /**
     * Map that represents game field.
     */
    val array: Array<Array<Node?>> = Array(h) { Array<Node?>(w) { null } }

    /**
     * State of Shape.
     */
    private var state:Boolean

    /**
     * Amount of not active Nodes on field.
     */
    private var nodes:Int

    /**
     * Current Shape.
     */
    private var shape: Shape

    /**
     * Type of next Shape.
     */
    lateinit var nextType: Type

    /**
     * Color of next Shape.
     */
    lateinit var nextColor: Color

    /**
     * Amount of completed lines.
     */
    val lines = Lines

    init {
        nodes = 0
        state = true
        initNextShape()
        shape = randomShape()
    }

    /**
     * Update process of game field.
     */
    fun update() {
        for (n in shape.body) {
            shape.right = !(n.x < w -1 && array[n.y][n.x + 1] != null)
            shape.left = !(n.x > 0 && array[n.y][n.x - 1] != null)
        }
        shape.update(input)
        if (nodes != 0) {
            for (n in shape.body) {
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

    /**
     * Generate new Shape and moves Nodes of old Shape to game field.
     */
    private fun newShape() {
        for (n in shape.body) {
            n.state = NOT_ACTIVE
            array[n.y][n.x] = n
            nodes++
        }
        shape = Shape(nextType, nextColor)
        initNextShape()
    }

    /**
     * Checks whether the field contains full lines.
     */
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
            lines.inc()
            checkLine()
        }
    }

    /**
     * Render process of game field.
     * Render game field (Shape + all not active Nodes).
     */
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

    /**
     * Generate Type and Color for next Shape.
     */
    private fun initNextShape() {
        val types = Type.values()
        val colors = Color.values()
        val random = Random()
        nextType = types[random.nextInt(types.size)]
        nextColor = colors[random.nextInt(colors.size)]
    }
}

