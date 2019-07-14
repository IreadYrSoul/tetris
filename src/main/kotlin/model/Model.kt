package model

import input.Input
import java.awt.Graphics
import java.util.*
import model.State.NOT_ACTIVE
import model.Level.LEVEL_1
import util.GameSerializer
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import config.Configuration.width as w

/**
 * Represents model of game field.
 */
class Model(val w: Int, val h: Int, private val input: Input) {

    /**
     * Map that represents game field.
     */
    var array: Array<Array<Node?>> = Array(h) { Array<Node?>(w) { null } }

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
    var nodes: Int

    /**
     * Show whether the game is on pause.
     */
    private var isPaused: Boolean

    /**
     * Current Shape.
     */
    var shape: Shape

    /**
     * Type of next Shape.
     */
    lateinit var nextType: Type

    /**
     * Color of next Shape.
     */
    lateinit var nextColor: NodeColor

    /**
     * Level (velocity) of game.
     */
    var level: Level

    /**
     * Amount of completed lines.
     */
    var lines: Lines

    /**
     * The game is over.
     */
    var gameOver: Boolean

    init {
        gameOver = false
        nodes = 0
        isPaused = false
        state = true
        initNextShape()
        level = LEVEL_1
        lines = Lines
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
        if (gameOver) {
            return
        }
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
            if (!gameOver) {
                newShape()
            }
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
                if (nodes != 0 && !gameOver) {
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
            shape.active = false
            if (!gameOver) {
                newShape()
            }
            checkLine()
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
                    if (n.y == 1) {
                        gameOver = true
                    }
                    shape.active = false
                    if (!gameOver) {
                        newShape()
                    }
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
        shape = Shape(nextType, nextColor, level)
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
            if (lines.get().toInt() % 20 == 0) {
                level++
            }
            checkLine()
        }
    }

    /**
     * Render process of game field.
     * Render game field (Shape + all not active Nodes).
     */
    fun render(g: Graphics) {
        if (!gameOver) {
            renderShadow(g)
        }
        shape.render(g)
        if (shape.isPaused) {
            pauseRender(g)
        }
        for (y in array.size - 1 downTo 0) {
            for (x in array[y].size - 1 downTo 0) {
                if (array[y][x] != null) {
                    array[y][x]!!.render(g)
                }
            }
        }
    }

    /**
     * Save game (Model, Shape, Lines, Level) to file.
     */
    fun saveToFile() {
        GameSerializer.write(this)
    }

    /**
     * Create new random instance of Shape.
     */
    private fun randomShape(): Shape {
        val types = Type.values()
        val colors = NodeColor.values()
        val random = Random()
        return Shape(types[random.nextInt(types.size)], colors[random.nextInt(colors.size)], level)
    }

    /**
     * Generate Type and Color for next Shape.
     */
    private fun initNextShape() {
        val types = Type.values()
        val colors = NodeColor.values()
        val random = Random()
        nextType = types[random.nextInt(types.size)]
        nextColor = colors[random.nextInt(colors.size)]
    }

    /**
     * Render frame which say that Game is paused.
     */
    private fun pauseRender(g: Graphics) {
        g as Graphics2D
        g.color = Color.WHITE
        g.fillRect(20, 240, 160, 60)
        g.color = Color.BLACK
        g.fillRect(22, 242, 156, 56)
        g.color = Color.WHITE
        g.font = Font("Arial", Font.BOLD, 16)
        g.drawString("PAUSE", 73, 275)
    }
}