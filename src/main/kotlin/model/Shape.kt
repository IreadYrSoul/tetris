package model

import config.Configuration.height as h
import config.Configuration.width as w

import model.Type.*
import java.awt.Graphics
import java.util.*
import kotlin.collections.ArrayList

/**
 * Represents Tetris shape.
 */
class Shape(var type: Type, var color: Color) {

    /**
     * The body of Shape (4 Nodes).
     */
    var body: Array<Node>

    /**
     * Temp copy of Shape body.
     */
    var cpBody: ArrayList<Node>

    /**
     * Able to move down.
     */
    var active: Boolean

    /**
     * Able to move left
     */
    var left = true

    /**
     * Able to move right.
     */
    var right = true

    /**
     * Min X of Shape.
     */
    private var minX: Int

    /**
     * Max X of Shape.
     */
    private var maxX: Int

    /**
     * Min Y of Shape.
     */
    private var minY: Int

    /**
     * Max Y of Shape.
     */
    private var maxY: Int

    /**
     * Moving down timer.
     */
    private lateinit var timer: Timer

    constructor (type:Type, color: Color, body:Array<Node>) : this(type, color) {
        this.body = body
        this.type = type
        this.color = color
    }

    init {
        active = true
        body = fill()
        cpBody = ArrayList()
        minX = minX()
        maxX = maxX()
        minY = minY()
        maxY = maxY()
        fall()
    }

    /**
     * Create Shape body.
     */
    private fun fill(): Array<Node> {
        val body = arrayListOf<Node>()
        when (type) {
            I -> {
                body.add(Node(5, 1, color))
                body.add(Node(5, 0, color))
                body.add(Node(5, 2, color))
                body.add(Node(5, 3, color))
            }
            O -> {
                body.add(Node(5, 0, color))
                body.add(Node(6, 0, color))
                body.add(Node(5, 1, color))
                body.add(Node(6, 1, color))
            }
            T -> {
                body.add(Node(5, 0, color))
                body.add(Node(5, 1, color))
                body.add(Node(4, 0, color))
                body.add(Node(6, 0, color))
            }
            Z -> {
                body.add(Node(5, 0, color))
                body.add(Node(4, 0, color))
                body.add(Node(5, 1, color))
                body.add(Node(6, 1, color))
            }
            S -> {
                body.add(Node(5, 0, color))
                body.add(Node(6, 0, color))
                body.add(Node(4, 1, color))
                body.add(Node(5, 1, color))
            }
            J -> {
                body.add(Node(5, 0, color))
                body.add(Node(4, 0, color))
                body.add(Node(6, 0, color))
                body.add(Node(6, 1, color))
            }
            L -> {
                body.add(Node(5, 0, color))
                body.add(Node(4, 0, color))
                body.add(Node(6, 0, color))
                body.add(Node(4, 1, color))
            }
        }
        return body.toTypedArray()
    }

    /**
     * Pause the game.
     */
    fun pause() {
        this.timer.cancel()
    }

    /**
     * Resume the game.
     */
    fun resume() {
        fall()
    }

    /**
     * Moves down Shape per 1 second.
     */
    private fun fall() {
        this.timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                down()
            }
        }
        this.timer.schedule(task, 0, 750)
    }

    /**
     * Moves Shape to left.
     */
    fun left() {
        if (left) {
            right = true
            if (active && minX > 0) {
                body.forEach { it.left() }
            }
            minX = minX()
        }
    }

    /**
     * Moves Shape to right.
     */
    fun right() {
        if (right) {
            left = true
            if (active && maxX < w - 1) {
                body.forEach { it.right() }
            }
            maxX = maxX()
        }
    }

    /**
     * Moves Shape to down.
     */
    fun down() {
        if (active && maxY < h - 1) {
            body.forEach { it.down() }
        } else {
            active = false
        }
        maxY = maxY()
    }

    /**
     * Rotates Shape around axis.
     */
    fun rotate(array: Array<Array<Node?>>) {
        if (type == O) {
            return
        }
        cpBody = ArrayList()
        for (n in body) {
            cpBody.add(n.clone())
        }
        for (n in body) {
            n.rotate(body[0])
        }
        minX = minX()
        maxX = maxX()
        maxY = maxY()
        minY = minY()
        if (minX < 0 || maxX > w - 1 || minY < 0 || maxY > h - 1) {
            body = cpBody.toTypedArray()
        }
        for (n in body) {
            if (array[n.y][n.x] != null) {
                body = cpBody.toTypedArray()
                minX = minX()
                maxX = maxX()
                maxY = maxY()
                minY = minY()
            }
        }
    }

    /**
     * Get max X of Shape.
     */
    fun maxX(): Int {
        maxX = body[0].x
        for (n in body) {
            if (n.x > maxX) {
                maxX = n.x
            }
        }
        return maxX
    }

    /**
     * Get min X of Shape.
     */
    fun minX(): Int {
        minX = body[0].x
        for (n in body) {
            if (n.x < minX) {
                minX = n.x
            }
        }
        return minX
    }

    /**
     * Get max Y of Shape.
     */
    fun maxY(): Int {
        maxY = body[0].y
        for (n in body) {
            if (n.y > maxY) {
                maxY = n.y
            }
        }
        return maxY
    }

    /**
     * Get min Y of Shape.
     */
    fun minY(): Int {
        minY = body[0].y
        for (n in body) {
            if (n.y < minY) {
                minY = n.y
            }
        }
        return minY
    }

    /**
     * Render Shape process.
     * Render of each Nodes of Shape body.
     */
    fun render(g: Graphics) {
        body.forEach { it.render(g) }
    }
}