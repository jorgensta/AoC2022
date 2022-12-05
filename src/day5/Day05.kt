import java.util.*

fun main() {
    val day = "day5"
    val filename = "Day05"

    val WHITESPACE = 32

    class Action(val numCrates: Int, val from: Int, val to: Int)
    class Crate(val name: Char) {
        override fun toString(): String {
            return "[$name]"
        }
    }
    class Stack(val crates: LinkedList<Crate>) {
        fun initializeAdd(crate: Crate) {
            crates.addLast(crate)
        }

        fun add(crate: Crate) {
            crates.addFirst(crate)
        }

        fun pop(): Crate {
            return crates.removeFirst()
        }

        fun addOrdered(crts: List<Crate>) {
            crts.reversed().forEach { add(it) }
        }

        fun popOrdered(n: Int): List<Crate> {
            val elements = crates.take(n)
            elements.forEach { pop() }
            return elements
        }

        override fun toString(): String {
            return "Stack(crates = ${crates.joinToString{ "[${it.name}]"}})"
        }
    }

    val stacks: MutableMap<Int, Stack> = mutableMapOf()

    fun setup(input: List<String>, endOfCratesInputLineNumber: Int) {
        input.forEachIndexed inputIndex@{ lineNumber, line ->
            val windows = line.toList().windowed(3, 4)
            if (lineNumber + 1 > endOfCratesInputLineNumber) {
                return@inputIndex
            }

            windows.forEachIndexed { index, window ->
                stacks.putIfAbsent(index + 1, Stack(LinkedList()))
                if (window.all { it.code == WHITESPACE }) {
                    return@forEachIndexed
                }
                stacks[index + 1]?.initializeAdd(Crate(window[1]))
            }
        }
    }

    fun lineToAction(line: String): Action {
        val digits = line.split(" ").mapNotNull { it.toIntOrNull() }
        return Action(digits[0], digits[1], digits[2])
    }
    
    fun doAction(action: Action) {
        (1..action.numCrates).forEach {
            val releasedCrate = stacks[action.from]?.pop()
            if (releasedCrate != null) {
                stacks[action.to]?.add(releasedCrate)
            }
        }
    }

    fun doActionStep2(action: Action) {
        val releasedCrates = stacks[action.from]?.popOrdered(action.numCrates)
        if (releasedCrates != null) {
            stacks[action.to]?.addOrdered(releasedCrates)
        }
    }

    fun part1(input: List<String>, endOfCratesInputLineNumber: Int, instructionStart: Int): String {
        setup(input, endOfCratesInputLineNumber)
        input
            .subList(instructionStart, input.size)
            .map { lineToAction(it) }
            .forEach { doAction(it) }

        return stacks.values.map { it.pop().name }.joinToString("")
    }

    fun part2(input: List<String>, endOfCratesInputLineNumber: Int, instructionStart: Int): String {
        setup(input, endOfCratesInputLineNumber)
        input
            .subList(instructionStart, input.size)
            .map { lineToAction(it) }
            .forEach { doActionStep2(it) }

        return stacks.values.map { it.pop().name }.joinToString("")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    // val partOneTest = part1(testInput, 3, 5)
    // check(partOneTest == "CMZ")

    // println("Part one: ${part1(input, 8, 10)}")

    // val partTwoTest = part2(testInput, 3, 5)
    // check(partTwoTest == "MCD")

    println("Part two: ${part2(input, 8, 10)}")
}
