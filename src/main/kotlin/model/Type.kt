package model


/**
 * Represents the type of tetris shape.
 */
enum class Type {

    /**  _ _
     *  |_|_|
     *  |_|_|
     */
    O,

    /**  _ _
     *  |_|_|_
     *    |_|_|
     */
    Z,

    /**    _ _
     *   _|_|_|
     *  |_|_|
     */
    S,

    /**  _ _ _ _
     *  |_|_|_|_|
     */
    I,

    /**  _ _ _
     *  |_|_|_|
     *    |_|
     */
    T,

    /**  _
     *  |_|_ _
     *  |_|_|_|
     */
    J,

    /**  _ _ _
     *  |_|_|_|
     *  |_|
     */
    L;

    fun getCode(): String {
        when (this) {
            O -> return "O"
            T -> return "T"
            Z -> return "Z"
            S -> return "S"
            I -> return "I"
            T -> return "T"
            J -> return "J"
            L -> return "L"
        }
    }
}