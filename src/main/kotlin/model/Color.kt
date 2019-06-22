package model

import java.awt.Color

/**
 * Color of Shape.
 */
enum class Color {

    /**
     * Red color.
     */
    RED,

    /**
     * Green color.
     */
    GREEN,

    /**
     * Blue color.
     */
    BLUE,

    /**
     * White color.
     */
    WHITE,

    /**
     * Violet color.
     */
    VIOLET,

    /**
     * Yellow color.
     */
    YELLOW,

    /**
     * Orange color.
     */
    ORANGE;

    /**
     * Get appropriate color.
     */
    fun get():Color {
        when(this) {
            RED -> return Color(255, 68, 68)
            GREEN -> return Color(0, 200, 81)
            BLUE -> return Color(66, 133, 244)
            WHITE -> return Color(255, 255, 255)
            VIOLET -> return Color(170, 102, 204)
            YELLOW -> return Color(248, 255, 70)
            ORANGE -> return Color(255, 187, 51)
        }
    }
}