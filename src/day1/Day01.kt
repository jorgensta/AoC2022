package day1

import readInput

fun main() {
    fun part1(input: List<String>): Int {

        var mostAmountOfCaloriesByElf = 0
        var currentCalories = 0

        input.forEach {
            if (it.isBlank()) {
                currentCalories = 0
                return@forEach
            }

            currentCalories += it.toInt()
            if (currentCalories > mostAmountOfCaloriesByElf) {
                mostAmountOfCaloriesByElf = currentCalories
            }
        }

        return mostAmountOfCaloriesByElf
    }

    fun part2(input: List<String>): Int {

        var currentCalories = 0
        val listOfCaloriesByElf = mutableListOf<Int>()

        input.forEach {
            if (it.isBlank()) {
                listOfCaloriesByElf.add(currentCalories)
                currentCalories = 0
                return@forEach
            }
            currentCalories += it.toInt()
        }

        return listOfCaloriesByElf.sorted().takeLast(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    // val testInput = readInput("Day01_test")
    // #check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
