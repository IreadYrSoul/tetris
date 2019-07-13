package model

import model.NodeColor.GREEN
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class NodeRotateTest(private val beforeX: Int,
                     private val beforeY: Int,
                     private val afterX: Int,
                     private val afterY: Int) {

    private val axis = Node(5, 5, GREEN)

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "x:{0} y:{1} -> x:{2} y:{3}")
        fun data() = listOf(
                arrayOf(5, 7, 3, 5),
                arrayOf(3, 5, 5, 3),
                arrayOf(5, 3, 7, 5),
                arrayOf(7, 5, 5, 7),
                arrayOf(5, 6, 4, 5),
                arrayOf(4, 5, 5, 4),
                arrayOf(5, 4, 6, 5),
                arrayOf(6, 5, 5, 6),
                arrayOf(6, 6, 4, 6),
                arrayOf(4, 6, 4, 4),
                arrayOf(4, 4, 6, 4),
                arrayOf(6, 4, 6, 6),
                arrayOf(7, 6, 4, 7),
                arrayOf(4, 7, 3, 4),
                arrayOf(3, 4, 6, 3),
                arrayOf(6, 3, 7, 6),
                arrayOf(6, 7, 3, 6),
                arrayOf(3, 6, 4, 3),
                arrayOf(4, 3, 7, 4),
                arrayOf(7, 4, 6, 7),
                arrayOf(7, 7, 3, 7),
                arrayOf(3, 7, 3, 3),
                arrayOf(3, 3, 7, 3),
                arrayOf(7, 3, 7, 7)
        )
    }

    @Test
    fun testRotate() {
        val node = Node(beforeX, beforeY, GREEN)
        node.rotate(axis)
        assertEquals(node.x, afterX)
        assertEquals(node.y, afterY)
    }
}