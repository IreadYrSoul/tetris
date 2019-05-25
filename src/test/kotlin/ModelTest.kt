import org.junit.Assert
import org.junit.Test

class ModelTest {

    @Test
    fun createModelTest() {
        val model = Model(20, 400, 600)
        val array:Array<IntArray> = model.array
        val w:Int = model.array.size
        val h:Int = model.array[0].size

        Assert.assertNotNull(array)
        Assert.assertEquals(400/20, w)
        Assert.assertEquals(600/20, h)
    }
}