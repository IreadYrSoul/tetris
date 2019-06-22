package model

import config.Configuration.height as h
import config.Configuration.width as w

import input.Input
import model.Type.*
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.util.*

/**
 * Represents Tetris shape.
 */
class Shape(var type: Type) {

    val nodes:Array<Node>
    val timer:Timer

    var minX:Int
    var maxX:Int
    var minY:Int
    var maxY:Int
    var active:Boolean

    init {
        active = true
        nodes = fill()

        minX = minX()
        maxX = maxX()
        minY = minY()
        maxY = maxY()

        timer = Timer()
        val task = object: TimerTask() {
            override fun run() {
                down()
            }
        }
        timer.schedule(task, 1000, 1000)
    }

    /**
     * Create shape body.
     */
    private fun fill(): Array<Node> {
        val body = arrayListOf<Node>()
        when(type) {
            I -> {
                body.add(Node(5, 0))
                body.add(Node(5, 1))
                body.add(Node(5, 2))
                body.add(Node(5, 3))
            }
            O -> {
                body.add(Node(5, 0))
                body.add(Node(6, 0))
                body.add(Node(5, 1))
                body.add(Node(6, 1))
            }
            T -> {
                body.add(Node(5, 1))
                body.add(Node(4, 0))
                body.add(Node(5, 0))
                body.add(Node(6, 0))
            }
            Z -> {
                body.add(Node(5, 0))
                body.add(Node(6, 0))
                body.add(Node(5, 1))
                body.add(Node(4, 1))
            }
            S -> {
                body.add(Node(5, 0))
                body.add(Node(6, 0))
                body.add(Node(4, 1))
                body.add(Node(5, 1))
            }
            J -> {
                body.add(Node(4, 0))
                body.add(Node(5, 0))
                body.add(Node(6, 0))
                body.add(Node(6, 1))
            }
            L -> {
                body.add(Node(4, 0))
                body.add(Node(5, 0))
                body.add(Node(6, 0))
                body.add(Node(4, 1))
            }
        }
        return body.toTypedArray()
    }

    /**
     * Moves Shape to left.
     */
    fun left() {
        if (active && minX > 0) {
            nodes.forEach { it.left() }
        }
        minX = minX()
    }

    /**
     * Moves Shape to right.
     */
    fun right() {
        if (active && maxX < w - 1) {
            nodes.forEach { it.right() }
        }
        maxX = maxX()
    }

    /**
     * Moves Shape to down.
     */
    fun down() {
        if (active && maxY < h - 1) {
            nodes.forEach { it.down() }
        } else {
            active = false
        }
        maxY = maxY()
    }

    /**
     * Rotates Shape around axis.
     */
    fun rotate() {
        if (type == O) {
            return
        }
        for(n in nodes) {
            n.rotate(nodes[0])
        }
        minX = minX()
        maxX = maxX()
    }

    fun maxX():Int {
        maxX = nodes[0].x
        for (n in nodes) {
            if (n.x > maxX) {
                maxX = n.x
            }
        }
        return maxX
    }

    fun minX():Int {
        minX = nodes[0].x
        for (n in nodes) {
            if (n.x < minX) {
                minX = n.x
            }
        }
        return minX
    }

    fun maxY():Int {
        maxY = nodes[0].y
        for (n in nodes) {
            if (n.y > maxY) {
                maxY = n.y
            }
        }
        return maxY
    }

    fun minY():Int {
        minY = nodes[0].y
        for (n in nodes) {
            if (n.y < minY) {
                minY = n.y
            }
        }
        return minY
    }

    /**
     *
     */
    fun update(input: Input) {
        if (input.getKey(KeyEvent.VK_LEFT)) {
            left()
            input.map[KeyEvent.VK_LEFT] = false
        }
        if (input.getKey(KeyEvent.VK_RIGHT)) {
            right()
            input.map[KeyEvent.VK_RIGHT] = false
        }
        if (input.getKey(KeyEvent.VK_DOWN)) {
            down()
            input.map[KeyEvent.VK_DOWN] = false
        }
        if (input.getKey(KeyEvent.VK_SPACE)) {
            rotate()
            input.map[KeyEvent.VK_SPACE] = false
        }
    }

    /**
     * Renders Shape.
     */
    fun render(g:Graphics) {
        nodes.forEach { it.render(g) }
    }
}