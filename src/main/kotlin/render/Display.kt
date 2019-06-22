package render


import java.awt.image.BufferStrategy
import javax.swing.ImageIcon
import javax.swing.JFrame

import config.Configuration.displayHeight as h
import config.Configuration.displeyWidth as w
import config.Configuration.title as appName
import config.Configuration.logo
import input.Input
import java.awt.*
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.util.*


/**
 * Represents application window.
 */
class Display(val input:Input) {

    private var created = false
    private var clearColor:Int = 0

    lateinit var window:JFrame
    lateinit var content:Canvas
    lateinit var buffer:BufferedImage
    lateinit var bufferData:IntArray
    lateinit var bufferGraphics: Graphics
    lateinit var bufferStrategy:BufferStrategy

    fun create(_clearColor:Int) {
        if (created){
            return
        }
        window = JFrame()
        window.title = appName
        window.iconImage = ImageIcon(Thread.currentThread().getContextClassLoader().getResource(logo)).image
        window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val size = Dimension(w, h)

        content = Canvas()
        content.setPreferredSize(size)

        window.isResizable = false
        window.contentPane.add(content)
        window.pack()
        window.add(input)
        window.setLocationRelativeTo(null)
        window.isVisible = true

        buffer = BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)
        bufferData = (buffer.raster.dataBuffer as DataBufferInt).data
        bufferGraphics = buffer.graphics

        (bufferGraphics as Graphics2D).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        clearColor = _clearColor

        content.createBufferStrategy(3)
        bufferStrategy = content.bufferStrategy

        created = true
    }

    fun clear() {
        Arrays.fill(bufferData, clearColor)
    }

    fun swapBuffers() {
        val g = bufferStrategy.drawGraphics
        g.drawImage(buffer, 0, 0, null)
        bufferStrategy.show()
    }

    fun getGraphics():Graphics2D {
        return bufferGraphics as Graphics2D
    }

    fun destroy() {
        if (!created)
            return
        window.dispose();
    }

//    public static void addInput(Input input){
//        window.add(input);
//    }
}