import engine.Game
import java.awt.EventQueue
import javax.swing.UIManager

/**
 * Launch the game.
 */
fun main(args: Array<String>) {
    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName())
    EventQueue.invokeLater {Game().start()}
}