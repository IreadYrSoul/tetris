package input

import javax.swing.*
import java.awt.event.ActionEvent

/**
 * Input keyboard tool.
 */
class Input : JComponent() {

    /**
     * Persist buttons state (false = released, true = pressed).
     * The length equals 256 for 128 different keys multiply by 2 state (is pressed/ is released).
     */
    val map: BooleanArray = BooleanArray(256) // array which persist buttons state (false = released, true = pressed).

    init {
        for (i in map.indices) {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2)
            actionMap.put(i * 2, object : AbstractAction() {
                override fun actionPerformed(e: ActionEvent) {
                    map[i] = true
                }
            })
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1)
            actionMap.put(i * 2 + 1, object : AbstractAction() {
                override fun actionPerformed(arg0: ActionEvent) {
                    map[i] = false
                }
            })
        }
    }

    /**
     * Get state for special key.
     * When map[special-key] return true, it mean special button was pressed,
     * and special Action for this button have to execute.
     */
    fun getKey(KeyCode: Int): Boolean {
        return map[KeyCode]
    }
}
