import Configuration.width as w
import Configuration.height as h

import Position.*
import State.ACTIVE

/**
 * Represents the cell of game field.
 */
class Node (var x:Int, var y:Int) {

    var pos:Position = R1

    var state:State = ACTIVE

    /**
     * Rotate node clockwise.
     */
    fun rotate() {
        when (pos) {
            R1 -> {

                pos = R2
            }
            R2 -> {

                pos = R3
            }
            R3 -> {

                pos = R4
            }
            R4 -> {

                pos = R1
            }
        }
    }

    /**
     * Move node to left.
     */
    fun left() {
        if (x > 0) {
            x --
        }
    }

    /**
     * Move node to right.
     */
    fun right() {
        if (x < w) {
            x++
        }
    }

    /**
     * Move node to down.
     */
    fun down() {
        if (y > h) {
            y--
        }
    }

}