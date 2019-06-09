package model

import config.Configuration.width as w
import config.Configuration.height as h

/**
 * Represents model of game field.
 */
class Model (w:Int, h:Int) {

    val array:Array<Array<Node?>> = Array(h) { Array<Node?>(w){ null} }

}

