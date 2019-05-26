import model.Node
import org.junit.Assert
import org.junit.Test

import config.Configuration.width as w
import config.Configuration.height as h
import model.Position.*

import org.junit.Before

/**
 * @author Alexander Naumov.
 */
class NodeTest {

    lateinit var node: Node

    @Before
    fun setIp() {
        node = Node(0, 0)
    }

    @Test
    fun testNodeRotate() {
        //TODO: full method model.Node.rotate() is not implemented yet.
        node.rotate()
        Assert.assertEquals(node.pos, R2)
        node.rotate()
        Assert.assertEquals(node.pos, R3)
        node.rotate()
        Assert.assertEquals(node.pos, R4)
        node.rotate()
        Assert.assertEquals(node.pos, R1)
    }

    @Test
    fun testNodeMoveToLeft() {
        for (n in 0..w * 2) {
            node.left()
        }
        Assert.assertTrue(node.x > -1)
    }

    @Test
    fun testNodeMoveToRight() {
        for (n in 0..w * 2) {
            node.right()
        }
        Assert.assertTrue(node.x < w + 1)
    }

    @Test
    fun testNodeMoveToBottom() {
        for (n in 0..h * 2) {
            node.down()
        }
        Assert.assertTrue(node.y < h + 1)
    }
}