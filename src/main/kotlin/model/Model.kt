package model

import input.Input
import java.awt.Graphics
import java.util.*
import model.State.NOT_ACTIVE
import java.awt.event.KeyEvent
import config.Configuration.width as w

/**
 * Represents model of game field.
 */
class Model(private val w: Int, private val h: Int, private val input: Input) {

    /**
     * Map that represents game field.
     */
    private val array: Array<Array<Node?>> = Array(h) { Array<Node?>(w) { null } }

    /**
     * Shadow of Shape.
     */
    private val shadow: Array<Node?>

    /**
     * State of Shape.
     */
    private var state: Boolean

    /**
     * Amount of not active Nodes on field.
     */
    private var nodes: Int

    /**
     * Show whether the game is on pause.
     */
    private var isPaused: Boolean

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
        isPaused = false
        state = true
        initNextShape()
        shape = randomShape()
        shadow = Array(4) { null }
        for (i in shape.body.indices) {
            shadow[i] = shape.body[i].clone()
        }
    }

    /**
     * Update process of game field.
     */
    fun update() {
        for (n in shape.body) {
            shape.right = !(n.x < w - 1 && array[n.y][n.x + 1] != null)
            if (!shape.right) break
            shape.left = !(n.x > 0 && array[n.y][n.x - 1] != null)
            if (!shape.right) break
        }
        input(input)
        if (nodes != 0) {
            collisCheck()
        }
        state = shape.active
        if (!state) {
            newShape()
            checkLine()
        }
    }

    /**
     * Handles Shape action.
     */
    private fun input(input: Input) {
        if (input.getKey(KeyEvent.VK_LEFT) && !isPaused) {
            shape.left()
            input.map[KeyEvent.VK_LEFT] = false
        }
        if (input.getKey(KeyEvent.VK_RIGHT) && !isPaused) {
            shape.right()
            input.map[KeyEvent.VK_RIGHT] = false
        }
        if (input.getKey(KeyEvent.VK_DOWN) && !isPaused) {
            shape.down()
            input.map[KeyEvent.VK_DOWN] = false
        }
        if (input.getKey(KeyEvent.VK_UP) && !isPaused) {
            shape.rotate(array)
            input.map[KeyEvent.VK_UP] = false
        }
        if (input.getKey(KeyEvent.VK_P)) {
            if (!isPaused) {
                isPaused = true
                shape.pause()
            } else {
                isPaused = false
                shape.resume()
            }
            input.map[KeyEvent.VK_P] = false
        }
        if (input.getKey(KeyEvent.VK_SPACE) && !isPaused) {
            while (shape.active) {
                shape.down()
                if (nodes != 0) {
                    for (n in shape.body) {
                        if (n.y < array.size - 1) {
                            if (array[n.y + 1][n.x] != null) {
                                shape.active = false
                                newShape()
                                checkLine()
                                input.map[KeyEvent.VK_SPACE] = false
                                return
                            }
                        }
                    }
                }
            }
            input.map[KeyEvent.VK_SPACE] = false
        }
        updateShadow()
    }

    /**
     * Generate thr "Shadow" of Shape.
     * Shows the place where the Shape will be installed,
     * if Shape will be move same.
     */
    private fun updateShadow() {
        for (i in shape.body.indices) {
            shadow[i] = shape.body[i].clone()
        }
        var a = true
        while (a) {
            for (n in shadow) {
                n!!.down()
                if (nodes == 0) {
                    if (n.y == h - 1) {
                        a = false
                    }
                } else {
                    if (n.y == h - 1 || (n.y < h - 1 && array[n.y + 1][n.x] != null)) a = false
                }
            }
            if (!a) return
        }
    }

    /**
     * Render shadow.
     */
    private fun renderShadow(g: Graphics) {
        shadow.forEach { it!!.renderShadow(g) }
    }

    /**
     * Check for vertically collisions.
     */
    private fun collisCheck() {
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
            for (i in y - 1 downTo 0) {
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
        renderShadow(g)
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