package day3

import readInput

fun main() {
    val day = "day3"
    val filename = "Day03"

    val alfabeth_lowercase = "abcdefghijklmnopqrstuvwxyz".lowercase()
    val alfabeth_uppercase = "abcdefghijklmnopqrstuvwxyz".uppercase()

    fun getCompartments(line: String) =
        Pair(line.slice(0 until line.length / 2), line.slice(line.length / 2 until line.length))

    fun getPriority(char: Char): Int = when (char in alfabeth_lowercase) {
        true -> alfabeth_lowercase.indexOf(char) + 1
        false -> alfabeth_uppercase.indexOf(char) + 26 + 1
    }

    fun occurrenceInAllCollections(char: Char, window: List<String>): Boolean {
        return window.all { it.contains(char) }
    }

    fun getBadge(window: List<String>): Char {
        return (alfabeth_lowercase + alfabeth_uppercase)
            .toList()
            .first { occurrenceInAllCollections(it, window) }
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it ->
            val compartments = getCompartments(it)
            val intersect = compartments.first.toList().intersect(compartments.second.toList())
            intersect.sumOf{ s -> getPriority(s) }
        }
    }

    fun part2(input: List<String>): Int {
        return input
            .windowed(3, 3)
            .sumOf { window ->
                val badge = getBadge(window)
                getPriority(badge)
            }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    val partOneTest = part1(testInput)
    check(partOneTest == 157)

    println("Part one: ${part1(input)}")

    val partTwoTest = part2(testInput)
    check(partTwoTest == 70)

    println("Part two: ${part2(input)}")
}
