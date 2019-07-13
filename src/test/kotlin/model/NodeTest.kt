package model

import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import model.NodeColor.GREEN
import model.State.NOT_ACTIVE

class NodeTest {

    lateinit var node:Node
    val x = 5
    val y = 5

    @Before
    fun setUp() {
        node = Node(x, y, GREEN)
    }

    @Test
    fun testLeft() {
        node.left()
        assertEquals(node.x, x - 1)
        assertEquals(node.y, y)
    }

    @Test
    fun testRight() {
        node.right()
        assertEquals(node.x, x + 1)
        assertEquals(node.y, y)
    }

    @Test
    fun testDown() {
        node.down()
        assertEquals(node.y, y + 1)
        assertEquals(node.x, x)
    }

    @Test
    fun testClone() {
        val clone = node.clone()
        assertEquals(clone.x, node.x)
        assertEquals(clone.y, node.y)
        node.right()
        node.down()
        assertNotEquals(clone.x, node.x)
        assertNotEquals(clone.y, node.y)
    }

    @Test
    fun testSerialize() {
        val active = node.serialize()
        node.state = NOT_ACTIVE
        val notActive = node.serialize()
        assertEquals(active, "5 5 G")
        assertEquals(notActive, "G")
    }
}