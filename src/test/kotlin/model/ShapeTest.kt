package model

import org.junit.Test
import model.NodeColor.RED
import model.Type.T
import model.Level.LEVEL_1
import org.junit.Assert.fail
import config.Configuration.width as w
import config.Configuration.height as h

class ShapeTest {

    val shape = Shape(T, RED, LEVEL_1)

    @Test
    fun testLeft() {
        for (i in 0 .. w) {
            shape.left()
        }
        shape.body.forEach { if (it.x  < 0) fail() }
    }

    @Test
    fun testRight() {
        for (i in 0 .. w) {
            shape.right()
        }
        shape.body.forEach { if (it.x > w) fail() }
    }

    @Test
    fun testDown() {
        for (i in 0 .. h) {
            shape.down()
        }
        shape.body.forEach { if (it.y > h) fail() }
    }
}