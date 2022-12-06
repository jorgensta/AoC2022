package day6

import readInput

fun main() {
    val day = "day6"
    val filename = "Day06"

    val testMarkers = mapOf(
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 5,
        "nppdvjthqldpwncqszvftbrmjlhg" to 6,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 10,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 11
    )

    val testMarkersTwo = mapOf(
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb" to 19,
        "bvwbjplbgvbhsrlpgdmjqwftvncz" to 23,
        "nppdvjthqldpwncqszvftbrmjlhg" to 23,
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" to 29,
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" to 26
    )

    fun testingTwo() {
        testMarkersTwo.entries.forEach { entry ->
            val datastream = entry.key
            val markerTruth = entry.value
            var marker = 0
            var markerFound = false

            datastream
                .windowed(14)
                .forEachIndexed { index, string ->
                    if (markerFound) return@forEachIndexed

                    val allCharsAreDistinct = string.toCharArray().distinct().size == 14
                    if (allCharsAreDistinct) {
                        println("Marker found!")
                        marker = 14 + index
                        markerFound = true
                    }
                }

            println("Marker: $marker. Truth: $markerTruth")
            check(marker == markerTruth)
        }
    }

    fun testing() {
        testMarkers.entries.forEach { entry ->
            val datastream = entry.key
            val markerTruth = entry.value
            var marker = 0
            var markerFound = false

            datastream
                .windowed(4)
                .forEachIndexed { index, string ->
                    if (markerFound) return@forEachIndexed

                    val allCharsAreDistinct = string.toCharArray().distinct().size == 4
                    if (allCharsAreDistinct) {
                        println("Marker found!")
                        marker = 4 + index
                        markerFound = true
                    }
                }

            println("Marker: $marker. Truth: $markerTruth")
            check(marker == markerTruth)
        }
    }

    // testing()
    testingTwo()

    fun part1(input: String): Int {
        var markerFound = false
        var marker = 0

        input
            .windowed(4)
            .forEachIndexed { index, string ->
                if (markerFound) return@forEachIndexed

                val allCharsAreDistinct = string.toCharArray().distinct().size == 4
                if (allCharsAreDistinct) {
                    println("Marker found!")
                    marker = 4 + index
                    markerFound = true
                }
            }

        return marker
    }

    fun part2(input: String): Int {
        var markerFound = false
        var marker = 0

        input
            .windowed(14)
            .forEachIndexed { index, string ->
                if (markerFound) return@forEachIndexed

                val allCharsAreDistinct = string.toCharArray().distinct().size == 14
                if (allCharsAreDistinct) {
                    println("Marker found!")
                    marker = 14 + index
                    markerFound = true
                }
            }

        return marker
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    // val partOneTest = part1(testInput)
    // check(partOneTest == 157)

    println("Part one: ${part1(input.joinToString(""))}")

    // val partTwoTest = part2(testInput)
    // check(partTwoTest == 70)

    println("Part two: ${part2(input.joinToString(""))}")
}
