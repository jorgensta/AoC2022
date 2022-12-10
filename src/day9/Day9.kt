package day9

import readInput

fun List<String>.instructions() = this.map { Pair(it.split(" ").first(), it.split(" ").last()) }

class RopePart() {

    val coordinates = Coordinate(0, 0)

    fun follow(parent: Coordinate) {
        if(touches(parent)) return
        checkDiffAndDoMove(parent)
    }

    private fun checkDiffAndDoMove(parent: Coordinate) {
        val diffX = (parent.x - coordinates.x)
        val diffY = (parent.y - coordinates.y)

        if (diffX < 0 && diffY < 0) {
            coordinates.decrementX()
            coordinates.decrementY()
            return
        }

        if (diffX > 0 && diffY > 0) {
            coordinates.incrementX()
            coordinates.incrementY()
            return
        }

        if (diffX > 0 && diffY < 0) {
            coordinates.incrementX()
            coordinates.decrementY()
            return
        }

        if (diffX < 0 && diffY > 0) {
            coordinates.decrementX()
            coordinates.incrementY()
            return
        }

        if(diffX > 0) {
            coordinates.incrementX()
            return
        }

        if(diffY > 0) {
            coordinates.incrementY()
            return
        }

        if(diffX < 0) {
            coordinates.decrementX()
            return
        }

        if(diffY < 0) {
            coordinates.decrementY()
            return
        }
    }

    fun move(direction: String) {
        when (direction) {
            "R" -> coordinates.incrementX()
            "U" -> coordinates.incrementY()
            "D" -> coordinates.decrementY()
            "L" -> coordinates.decrementX()
        }
    }

    fun touches(parentCoordinates: Coordinate): Boolean {
        return touchesDiagonally(parentCoordinates) || touchesVerticalOrHorizontal(parentCoordinates) ||
                onTopOfEachOther(parentCoordinates)
    }

    private fun onTopOfEachOther(parent: Coordinate): Boolean {
        return parent.x == coordinates.x && parent.y == coordinates.y
    }

    private fun touchesDiagonally(parent: Coordinate): Boolean {
        val topRight = parent.x == coordinates.x + 1 && parent.y == coordinates.y + 1
        val topLeft = parent.x == coordinates.x + 1 && parent.y == coordinates.y - 1
        val bottomRight = parent.x == coordinates.x - 1 && parent.y == coordinates.y + 1
        val bottomLeft = parent.x == coordinates.x - 1 && parent.y == coordinates.y - 1
        return topRight || topLeft || bottomLeft || bottomRight
    }

    private fun touchesVerticalOrHorizontal(parent: Coordinate): Boolean {
        val verticalTouch = (parent.x == coordinates.x + 1 || coordinates.x - 1 == parent.x) && coordinates.y == parent.y
        val horizontalTouch = (parent.y == coordinates.y + 1 || coordinates.y - 1 == parent.y) && coordinates.x == parent.x
        return verticalTouch || horizontalTouch
    }

    override fun toString(): String {
        return coordinates.coordinateString()
    }
}

data class Coordinate(var x: Int, var y: Int) {
    fun incrementX() = x++
    fun incrementY() = y++
    fun decrementX() = x--
    fun decrementY() = y--
    fun coordinateString(): String = "$x.$y"
    fun setCoordinates(coordinate: Coordinate) {
        y = coordinate.y
        x = coordinate.x
    }
}

class Gorge() {
    private val rope = (0..9).map { RopePart() }
    private val visitedSet = mutableListOf<String>()

    fun runRopeSimulator(input: List<String>) {
        visitedSet.add(Coordinate(0,0).toString())
        input.instructions().forEach(::doInstruction)
    }


    private fun doInstruction(instr: Pair<String, String>) {
        val direction = instr.first
        val steps = instr.second.toInt()
        repeat(steps) { moveRopeInDirection(direction, steps) }
    }

    private fun moveRopeInDirection(direction: String, steps: Int) {
        // println(this)
        val head = rope.first()
        head.move(direction)
        rope.zipWithNext().forEach{ it.second.follow(it.first.coordinates) }
        visitedSet.add(rope.last().coordinates.toString())
    }

    override fun toString(): String {
        val grid = (0..5).map { (0..5).map { "." }.toMutableList() }.reversed().toMutableList()
        rope.forEachIndexed { index, ropePart ->
            grid[ropePart.coordinates.x][ropePart.coordinates.y] = index.toString()
        }
        return grid.map { row -> row.joinToString(" ") + "\n" }.joinToString("")
    }

    fun numVisited(): Int = visitedSet.toSet().size
}

fun part1(input: List<String>): Int {
    val gorge = Gorge()
    gorge.runRopeSimulator(input)
    return gorge.numVisited()
}

fun part2(input: List<String>): Int {
    val gorge = Gorge()
    gorge.runRopeSimulator(input)
    return gorge.numVisited()
}

fun main() {

    /*
    the head (H) and tail (T) must always be touching. Diagonally adjacent and even overlapping both count as touching.
    The (H) can cover the (T).
    Its always the head (H) that moves, the (T) follows T.
    */

    val day = "day09"
    val filename = "Day09"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    // val partOneTest = part1(testInput)
    // check(partOneTest == 13)

    // println("Part one: ${part1(input)}")

    val partTwoTest = part2(testInput)
    check(partTwoTest == 36)

    println("Part two: ${part2(input)}")
}
