package model

import model.Type.*

/**
 * Represents tetris shape.
 */
class Shape {

    val nodes:Array<Node>? = null

    private val type: Type = Type.T

    /**
     * Create shape body.
     */
    fun fill(): Array<Node> {
        when(type) {
            I -> {

            }
            O -> {

            }
            T -> {

            }
            Z -> {

            }
            S -> {

            }
            J -> {

            }
            L -> {

            }
        }
        return arrayOf(Node(0, 0), Node(0, 0), Node(0, 0), Node(0, 0))
    }

    companion object {

        /**
         * Create new instance of model.Shape.
         */
        fun newInstance(type: Type): Shape {
            return Shape()
        }
    }
}