package model


/**
 * Represents the type of tetris shape.
 */
enum class Type (val code: String) {

    /**  _ _
     *  |_|_|
     *  |_|_|
     */
    O("O"),

    /**  _ _
     *  |_|_|_
     *    |_|_|
     */
    Z("Z"),

    /**    _ _
     *   _|_|_|
     *  |_|_|
     */
    S("S"),

    /**  _ _ _ _
     *  |_|_|_|_|
     */
    I("I"),

    /**  _ _ _
     *  |_|_|_|
     *    |_|
     */
    T("T"),

    /**  _
     *  |_|_ _
     *  |_|_|_|
     */
    J("J"),

    /**  _ _ _
     *  |_|_|_|
     *  |_|
     */
    L("L")

}