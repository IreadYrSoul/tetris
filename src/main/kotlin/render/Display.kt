package render


import java.awt.Canvas
import java.awt.Color
import java.awt.Dimension
import java.awt.image.BufferStrategy
import javax.swing.ImageIcon
import javax.swing.JFrame

import config.Configuration.displayHeight as h
import config.Configuration.displeyWidth as w
import config.Configuration.title as appName
import config.Configuration.logo

/**
 * Represents application window.
 */
class Display: JFrame() {

    private val canvas: Canvas = Canvas()
    private val icon: ImageIcon = ImageIcon(logo)
    val bs: BufferStrategy

    init {
        canvas.size = (Dimension(w, h))
        canvas.background = Color.BLACK
        canvas.ignoreRepaint = true

        contentPane.add(canvas)
        iconImage = icon.image
        title = appName
        ignoreRepaint = true
        setLocationRelativeTo(null)
        pack()
        isVisible = true

        canvas.createBufferStrategy(2)
        bs = canvas.bufferStrategy

        addWindowListener(DisplayListener())
    }
}