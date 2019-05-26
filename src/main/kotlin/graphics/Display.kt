package graphics


import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame

import config.Configuration.displayHeight as h
import config.Configuration.displeyWidth as w
import config.Configuration.title as appName

/**
 * @author Alexander Naumov.
 */

class Display: JFrame() {

    val canvas: Canvas

    init {
        canvas = Canvas()
        canvas.size = (Dimension(w, h))
        canvas.background = Color.BLACK

        contentPane.add(canvas)
        title = appName
        ignoreRepaint = true
        pack()
    }
}