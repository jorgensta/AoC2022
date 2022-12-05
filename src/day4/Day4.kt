package day4

import readInput

fun main() {
    val day = "day4"
    val filename = "Day04"

    class Section(val intRange: IntRange) {
        fun containedBy(other: Section): Boolean {
            return intRange.intersect(other.intRange).size == intRange.toList().size
        }

        fun overlappedBy(other: Section): Boolean {
            return intRange.intersect(other.intRange).isNotEmpty()
        }
    }

    fun part1(input: List<String>): Int {
        return input.count {
            val pair = it.split(",")
            val sectionPair = pair.map {
                val range = it.split("-").first().toInt()..it.split("-").last().toInt()
                Section(range)
            }
            val first = sectionPair.first()
            val second = sectionPair.last()

            first.containedBy(second) || second.containedBy(first)
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val pair = it.split(",")
            val sectionPair = pair.map {
                val range = it.split("-").first().toInt()..it.split("-").last().toInt()
                Section(range)
            }
            val first = sectionPair.first()
            val second = sectionPair.last()

            first.overlappedBy(second) || second.overlappedBy(first)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    val partOneTest = part1(testInput)
    check(partOneTest == 2)

    println("Part one: ${part1(input)}")

    val partTwoTest = part2(testInput)
    check(partTwoTest == 4)

    println("Part two: ${part2(input)}")
}
