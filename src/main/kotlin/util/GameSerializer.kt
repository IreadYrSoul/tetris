package util

import input.Input
import model.*
import model.Type.*
import model.Color.*
import java.io.File
import java.lang.IllegalArgumentException
import config.Configuration.filePath as path

/**
 * Serializes and deserializes Game to File.
 */
object GameSerializer {

    /**
     * Represents file that contains
     * info about saved game (Model).
     */
    private val file = File(path)

    /**
     * Save game.
     * Writes Model (game) to .dat file
     */
    fun write(model: Model) {
        file.printWriter().use { 
            //write lines (score).
            it.print("[Lines]\n")
            it.print(model.lines.get())
            it.print("\n\n")
            //write shape
            it.print("[Shape]\n")
            it.print("type=${model.shape.type.getCode()} color=${model.shape.color.getCode()}\n")
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
        val shape = Shape(getType(type), getColor(color), list.toTypedArray())
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
     * Get Color of Node by string code.
     */
    private fun getColor(code:String): model.Color {
        when(code) {
            "R" -> return RED
            "G" -> return GREEN
            "B" -> return BLUE
            "W" -> return WHITE
            "V" -> return VIOLET
            "Y" -> return YELLOW
            "O" -> return ORANGE
             else -> throw IllegalArgumentException()
        }
    }

    /**
     * Get Type of Shape by string code.
     */
    private fun getType(code:String):model.Type {
        when(code) {
            "T" -> return T
            "O" -> return O
            "Z" -> return Z
            "S" -> return S
            "I" -> return I
            "L" -> return L
            "J" -> return J
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
                builder.append((n?.serialize() ?: "N" )+ "-")
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