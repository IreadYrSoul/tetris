package model
/**
 * Represents rotation position around axis.
 */
enum class Position {

    /**
     *     |  _
     *     | |_|
     *_____|_____
     *     |
     *     |
     *     |
     */
    R1,

    /**
     *     |
     *     |
     *_____|_____
     *     |  _
     *     | |_|
     *     |
     */
    R2,

    /**
     *     |
     *     |
     *_____|_____
     *  _  |
     * |_| |
     *     |
     */
    R3,

    /**
     *  _  |
     * |_| |
     *_____|_____
     *     |
     *     |
     *     |
     */
    R4

}