package util

import input.Input
import model.Level.LEVEL_3
import model.Lines
import model.NodeColor.RED
import model.Shape
import model.Node
import model.Type.T
import model.Model
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

import config.Configuration.height as h
import config.Configuration.width as w
import config.Configuration.filePath as path


class GameSerializerTest {

    private val shape = Shape(T, RED, LEVEL_3)
    private val model = Model(w, h, Input())
    private val lines: Lines = Lines

    lateinit var file: File

    @Before
    fun setUp() {
        lines.points = 80
        shape.body = arrayOf(Node(5, 10, RED), Node(5, 11, RED), Node(4, 10, RED), Node(6, 10, RED))
        model.shape = shape
    }

    @After
    fun tearDown() {
        if (file.exists()) {
            file.delete()
        }
    }

    @Test
    fun testWrite() {
        GameSerializer.write(model)
        file = File(path)

        Assert.assertTrue(file.exists())
        Assert.assertEquals(path, file.path)
    }

    @Test
    fun testRead() {
        GameSerializer.write(model)
        file = File(path)

        val newModel = GameSerializer.read(Input())
        Assert.assertNotNull(newModel)

        val newShape = newModel!!.shape
        Assert.assertNotNull(newShape)
        Assert.assertEquals(RED, newShape.nodeColor)
        Assert.assertEquals(T, newShape.type)
        Assert.assertEquals(LEVEL_3, newShape.level)
        Assert.assertTrue(newShape.active)
    }
}