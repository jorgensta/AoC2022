fun asGrid(input: List<String>) = input.map {
    it
        .split("")
        .mapNotNull { s -> s.toIntOrNull() }
}

fun countPerimeter(grid: List<List<Int>>): Int {
    val height = grid.size
    val width = grid.first().size
    return (height + width) * 2 - 4 // counts the corners four times ;)
}

fun visibleFromRight(row: Int, column: Int, grid: List<List<Int>>): Boolean {
    val width = grid.first().size
    val toCheck = grid[row].subList(column + 1, width)
    return toCheck.all { grid[row][column] > it }
}

fun visibleFromLeft(row: Int, column: Int, grid: List<List<Int>>): Boolean {
    val toCheck = grid[row].subList(0, column).reversed()
    val value = grid[row][column]
    return toCheck.all { value > it }
}

fun gridToFixedColumn(column: Int, grid: List<List<Int>>): List<Int> {
    return grid.map { it[column] }
}

fun visibleFromBottom(row: Int, column: Int, grid: List<List<Int>>): Boolean {
    val toCheck = gridToFixedColumn(column, grid).subList(row + 1, grid.first().size)
    return toCheck.all { grid[row][column] > it }
}

fun visibleFromTop(row: Int, column: Int, grid: List<List<Int>>): Boolean {
    val toCheck = gridToFixedColumn(column, grid).subList(0, row)
    return toCheck.all { grid[row][column] > it }
}

fun visible(row: Int, column: Int, grid: List<List<Int>>): Boolean = visibleFromLeft(row, column, grid) ||
        visibleFromBottom(row, column, grid) ||
        visibleFromTop(row, column, grid) ||
        visibleFromRight(row, column, grid)

fun visibleFromOutside(grid: List<List<Int>>): Int {
    var count = 0
    for (row in 1 until grid.size - 1) { // Shrink grid on both top and bottom
        for (column in 1 until grid[row].size - 1) {
            if (visible(row, column, grid)) count++
        }
    }
    return count
}

fun gotBlocked(height: Int, otherHeight: Int) = otherHeight >= height

fun calculate(height: Int, treesInOneDirection: List<Int>): Int {
    var count = 0
    for (otherHeight in treesInOneDirection) {
        if (gotBlocked(height, otherHeight)) {
            count ++
            break
        }
        if (height > otherHeight) count++
    }

    return count
}

fun calculateScenicScore(row: Int, col: Int, grid: List<List<Int>>): Int {
    val top = gridToFixedColumn(col, grid).subList(0, row).reversed()
    var right = grid[row].subList(col + 1, grid.first().size)
    if (col == grid.first().size) right = emptyList()
    var bottom = gridToFixedColumn(col, grid).subList(row + 1, grid.first().size)
    if (row == grid.size) bottom = emptyList()
    val left = grid[row].subList(0, col).reversed()

    val tree = grid[row][col]
    return calculate(tree, top) * calculate(tree, right) * calculate(tree, bottom) * calculate(tree, left)
}

fun bestScenicScore(grid: List<List<Int>>): Int {
    val scenicScores = mutableListOf<Int>()

    for (row in 0..grid.size - 1) {
        for (col in 0..grid.first().size - 1) {
            scenicScores.add(calculateScenicScore(row, col, grid))
        }
    }
    return scenicScores.max()
}

fun part1(input: List<String>): Int {
    val grid = asGrid(input)
    return countPerimeter(grid) + visibleFromOutside(grid)
}

fun part2(input: List<String>): Int {
    return bestScenicScore(asGrid(input))
}

fun main() {
    val day = "day8"
    val filename = "Day08"

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    val partOneTest = part1(testInput)
    check(partOneTest == 21)

    println("Part one: ${part1(input)}")

    val partTwoTest = part2(testInput)
    check(partTwoTest == 8)

    println("Part two: ${part2(input)}")
}
