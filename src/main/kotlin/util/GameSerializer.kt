package util

import input.Input
import model.*
import java.io.File
import model.NodeColor.*
import model.Type.*
import model.Level.*
import config.Configuration.filePath as path

/**
 * Serializes and deserializes Game to File.
 */
object GameSerializer {

    /**
     * Represents file that contains
     * info about saved game (Model).
     */
    private var file = File(path)

    /**
     * Save game.
     * Writes Model (game) to .dat file
     */
    fun write(model: Model) {
        if (!file.parentFile.exists())
            file.parentFile.mkdirs()
        if (!file.exists())
            file.createNewFile()
        file.printWriter().use { 
            //write lines (score).
            it.print("[Lines]\n")
            it.print(model.lines.get())
            it.print("\n\n")
            //write level
            it.print("[Level]\n")
            it.print(model.shape.level.code)
            it.print("\n\n")
            //write shape
            it.print("[Shape]\n")
            it.print("type=${model.shape.type.code} nodeColor=${model.shape.nodeColor.code}\n")
            it.print(createLine(model.shape.body.toList()))
            it.print("\n\n")
            //write model
            it.print("[Model]\n")
            it.print("width=${model.w} height=${model.h}\n")
            for (line in model.array) {
                it.print(createLine(line.toList()))
                if (model.array.indexOf(line) < model.array.size - 1) it.print("\n")
            }
        }
    }

    /**
     * Load game.
     * Reads .dat file and convert it to Model.
     */
    fun read(input: Input):Model? {
        var lineIndex = 0
        var levelIndex = 0
        var shapeIndex = 0
        var modelIndex = 0
        var nodes = 0
        if (!file.exists()) {
            return null
        }
        val lines = file.readLines()
        file.delete()
        for (i in lines.indices) {
            if (lines[i] == "[Lines]") {
                lineIndex = i + 1
            }
            if (lines[i] == "[Level]") {
                levelIndex = i + 1
            }
            if (lines[i] == "[Shape]") {
                shapeIndex = i + 1
            }
            if (lines[i] == "[Model]") {
                modelIndex = i + 1
                break
            }
        }
        //read lines (score).
        val score = lines[lineIndex]
        Lines.points = score.toInt()

        //read level.
        val lvlCode = lines[levelIndex]
        val level = getLevel(lvlCode)

        //read shape.
        val list = arrayListOf<Node>()
        val params = lines[shapeIndex].split(" ")
        val type = params[0].split("=")[1]
        val color = params[1].split("=")[1]
        val shapeLine = lines[shapeIndex + 1].removeBraces().split("-")
        for (code in shapeLine) {
            val codes = code.split(" ")
            list.add(Node(codes[1].toInt(), codes[0].toInt(), getColor(codes[2])))
        }
        val shape = Shape(getType(type), getColor(color), level, list.toTypedArray())
        //read model.
        val width = lines[modelIndex].split(" ")[0]
        val height = lines[modelIndex].split(" ")[1]
        val w = width.split("=")[1].toInt()
        val h = height.split("=")[1].toInt()
        val array = Array(h) { Array<Node?>(w) { null } }
        for (i in modelIndex + 1 until lines.size) {
            val line = lines[i].removeBraces().split("-")
            for (j in line.indices) {
                if (line[j] != "N") {
                    val node = Node(j,i - (modelIndex + 1 ), getColor(line[j]))
                    node.state = State.NOT_ACTIVE
                    array[i - (modelIndex + 1 )][j] = node
                    nodes++
                }
            }
        }
        val model = Model(w, h, input)
        model.shape = shape
        model.array = array
        model.nodes = nodes
        return model
    }

    /**
     * Get NodeColor of Node by string code.
     */
    private fun getColor(code:String): NodeColor {
        return when(code) {
            "R" -> RED
            "G" -> GREEN
            "B" -> BLUE
            "W" -> WHITE
            "V" -> VIOLET
            "Y" -> YELLOW
            "O" -> ORANGE
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Get Type of Shape by string code.
     */
    private fun getType(code:String):Type {
        return when(code) {
            "T" -> T
            "O" -> O
            "Z" -> Z
            "S" -> S
            "I" -> I
            "L" -> L
            "J" -> J
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Get Level of Game (Shape) by code.
     */
    private fun getLevel(code: String): Level {
        return when(code) {
            "LEVEL 1" -> LEVEL_1
            "LEVEL 2" -> LEVEL_2
            "LEVEL 3" -> LEVEL_3
            "LEVEL 4" -> LEVEL_4
            "LEVEL 5" -> LEVEL_5
            "LEVEL 6" -> LEVEL_6
            "LEVEL 7" -> LEVEL_7
            "LEVEL 8" -> LEVEL_8
            "LEVEL 9" -> LEVEL_9
            else -> throw IllegalArgumentException()
        }
    }

    /**
     * Build string with '-' as separator.
     */
    private fun createLine(nodes:List<Node?>):String {
        val builder = StringBuilder()
        builder.append("[")
        var n:Node?
        for (i in nodes.indices) {
            n = nodes[i]
            if (i < nodes.size -1) {
                builder.append((n?.serialize() ?: "N" ) + "-")
            } else {
                builder.append((n?.serialize() ?: "N"))
            }
        }
        builder.append("]")
        return builder.toString()
    }

    /**
     * Remove first and last brace of line "[]".
     */
    private fun String.removeBraces() =  this.substring(1, this.length - 1)
}