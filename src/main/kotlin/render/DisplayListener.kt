package render

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

/**
 * Handles receiving window events.
 */
class DisplayListener: WindowAdapter() {

    /**
     * Handles the Display window closing.
     */
    override fun windowClosing(e: WindowEvent?) {
        println("shutdown wondow.")
        System.exit(0)
    }

    private fun shutDown() {
        //TODO: Not implemented yet.
    }

}