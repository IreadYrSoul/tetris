package model

/**
 * Represents level of game.
 * Velocity with which the Shape will move.
 */
enum class Level(val value:Long, val code:String) {

    LEVEL_1(750, "LEVEL 1"),

    LEVEL_2(675, "LEVEL 2"),

    LEVEL_3(600, "LEVEL 3"),

    LEVEL_4(525, "LEVEL 4"),

    LEVEL_5(450, "LEVEL 5"),

    LEVEL_6(375, "LEVEL 6"),

    LEVEL_7(300, "LEVEL 7"),

    LEVEL_8(225, "LEVEL 8"),

    LEVEL_9(150, "LEVEL 9");

    /**
     * Generate next level.
     */
    operator fun inc(): Level {
        return when(this) {
            LEVEL_1 -> LEVEL_2
            LEVEL_2 -> LEVEL_3
            LEVEL_3 -> LEVEL_4
            LEVEL_4 -> LEVEL_5
            LEVEL_5 -> LEVEL_6
            LEVEL_6 -> LEVEL_7
            LEVEL_7 -> LEVEL_8
            LEVEL_8 -> LEVEL_9
            LEVEL_9 -> LEVEL_1
        }
    }
}