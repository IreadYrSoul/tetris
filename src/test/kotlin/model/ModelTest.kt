package model

import model.Model
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class ModelTest {

    val h = 20
    val w = 10

    @Test
    fun testLeft() {
        val model = Model(w, h, any())
    }
}