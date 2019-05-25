/**
 * Represents the cell of game field.
 */
class Node (var x:Int, var y:Int) {

    /**
     * Rotate node clockwise.
     */
    fun rotate() {

    }

    /**
     * Move node to left.
     */
    fun left() {
        if (x > 0) {
            x--
        }
    }

    /**
     * Move node to right.
     */
    fun right() {
        if (x < 50) {
            x++
        }
    }

    /**
     * Move node to down.
     */
    fun down() {
        if (y > 50) {
            y--
        }
    }

}