package day2

import readInput

fun main() {

    val rockPoints = 1
    val paperPoints = 2
    val scissorsPoints = 3

    val rules = mapOf(
        "B" to "A",
        "C" to "B",
        "A" to "C"
    )

    val mapshit = mapOf(
        "X" to "A",
        "Y" to "B",
        "Z" to "C"
    )

    val points = mapOf(
        "A" to rockPoints,
        "B" to paperPoints,
        "C" to scissorsPoints,
        "X" to rockPoints,
        "Y" to paperPoints,
        "Z" to scissorsPoints
    )

    fun part1(input: List<String>): Int {
        return input.map { it.split(" ") }.map {
            var winDrawLose = 0
            val opponent = it.first()
            val me = mapshit[it.last()]

            winDrawLose += points[me]!!

            if (me == opponent) {
                winDrawLose += 3
            }

            if (rules[opponent] == me) {
                winDrawLose += 6
            }
            return@map winDrawLose
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { it.split(" ") }.map {
            var winDrawLose = 0

            // if Rock (A) -> Draw, score = 4
            // if Paper (B) -> Lose, score = 1
            // if Scissor (C) -> WIN, score = 7

            val me = mapshit[it.last()]
            val action = it.last()
            val opponent = it.first()

            if (action == "Y") {
                // make it a draw
                winDrawLose += points[opponent]!! // i.e. if opponent choose Rock (A), give me the same points.
                winDrawLose += 3
                return@map winDrawLose// i.e. draw gets me 3 points
            }

            if (action == "Z") {
                // make it a win
                // it.key is the winning move, so we find it.value, and then take the key
                val myMove = rules.filter { it.value == opponent }.firstNotNullOf { it.key }
                winDrawLose += points[myMove]!!
                winDrawLose += 6
                return@map winDrawLose

            }

            if (action == "X") {
                // Lose
                // it.value is the losing move, so we find, get the key to lose.
                val myMove = rules.filter { it.key == opponent }.firstNotNullOf { it.value }
                winDrawLose += points[myMove]!!
                return@map winDrawLose
            }

            return@map winDrawLose
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day2/Day02_test")
    val partTwoTest = part2(testInput)
    check(partTwoTest == 12)

    val input = readInput("/day2/Day02")
    println(part1(input))
    println(part2(input))
}
