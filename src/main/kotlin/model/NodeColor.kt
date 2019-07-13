package model

import java.awt.Color

/**
 * Color of Shape.
 */
enum class NodeColor(val value:Color, val code:String) {

    /**
     * Red nodeColor.
     */
    RED(Color(235, 77, 75),"R"),

    /**
     * Green nodeColor.
     */
    GREEN(Color(106, 176, 76),"G"),

    /**
     * Blue nodeColor.
     */
    BLUE(Color(104, 109, 224),"B"),

    /**
     * White nodeColor.
     */
    WHITE(Color(255, 255, 255),"W"),

    /**
     * Violet nodeColor.
     */
    VIOLET(Color(224, 86, 253),"V"),

    /**
     * Yellow nodeColor.
     */
    YELLOW(Color(249, 202, 36),"Y"),

    /**
     * Orange nodeColor.
     */
    ORANGE(Color(240, 147, 43),"O")

}