package config
/**
 * Contains configuration app properties.
 */
object Configuration {

    /**
     * the application name.
     */
    const val title = "Tetris"

    /**
     *  the Display height.
     */
    const val displayHeight = 400

    /**
     * the Display width.
     */
    const val displeyWidth = 200

    /**
     * the model.Node width/height.
     */
    const val nodeSize = 20

    /**
     * the number Nodes of horizontally.
     */
    const val height = displayHeight / nodeSize

    /**
     * the number Nodes of vertically.
     */
    const val width = displeyWidth / nodeSize
}