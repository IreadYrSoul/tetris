package model

import config.Configuration.width as w
import config.Configuration.height as h

/**
 * Represents model of game field.
 */
class Model {
    var array:Array<IntArray> = Array(w){ IntArray(h) }
}

