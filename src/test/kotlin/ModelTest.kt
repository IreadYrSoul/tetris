import org.junit.Assert
import org.junit.Test

import Configuration.height as h
import Configuration.width as w

class ModelTest {

    @Test
    fun createModelTest() {
        val model = Model()
        val array:Array<IntArray> = model.array
        val width:Int = model.array.size
        val height:Int = model.array[0].size

        Assert.assertNotNull(array)
        Assert.assertEquals(w, width)
        Assert.assertEquals(h, height)
    }
}