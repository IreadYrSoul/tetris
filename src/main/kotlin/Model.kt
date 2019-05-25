/**
 * Represents model of game field.
 */
class Model (val n:Int, val width:Int, val height:Int) {
    var array:Array<IntArray> = Array(width / n){ IntArray(height / n) }
}

