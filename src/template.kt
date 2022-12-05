fun main() {
    val day = "day4"
    val filename = "Day04"

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
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
