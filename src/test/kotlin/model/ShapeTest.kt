package model

import org.junit.Test
import model.Color.RED
import model.Type.T
import org.junit.Assert
import config.Configuration.width as w
import config.Configuration.height as h

class ShapeTest {

    val shape = Shape(T, RED)

    @Test
    fun testLeft() {
        for (i in 0 .. w) {
            shape.left()
        }
        Assert.assertTrue(shape.minX() == 0)
    }

    @Test
    fun testRight() {
        for (i in 0 .. w) {
            shape.right()
        }
        Assert.assertTrue(shape.maxX() < w)
    }

    @Test
    fun testDown() {
        for (i in 0 .. h) {
            shape.down()
        }
        Assert.assertTrue(shape.maxY() < h)
    }

    @Test
    fun rotateTest() {
        val array = Array(h) { Array<Node?>(w) { null } }
        for (n in shape.body) {
            var count = 0
            if (n.x == 5) {
                count++
            }


        }
        shape.rotate(array)
    }
}