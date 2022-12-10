class CPU() {
    val sprite = Sprite()
    var shift = 0
    var cycles = 0
    var X = 1
    val values = mutableListOf<Int>()

    fun doTick() {
        cycles++
        checkForMod20()
        sprite.draw(cycles, X+shift)
    }

    private fun checkForMod20() {
        if(cycles.mod(40) == 20) {
            values.add(X * cycles)
        }

        if(cycles.mod(40) == 0){
            shift+=40
        }
    }

    fun addx(x: Int) {
        X += x
    }

    fun doInstruction(instruction: String) {
        if(instruction.contains("addx")) {
            // After two cycles, addX
            doTick()
            doTick()
            addx(instruction.split(" ").last().toInt())
            return
        }

        if (instruction == "noop") {
            // Takes one cycle to complete
            doTick()
        }
    }

    fun result(): Int = values.sum()
}


class Sprite() {
    val CRT = (0..6*40 - 1).map { "." }.toMutableList()

    fun draw(cycle: Int, X: Int) {
        val sprite = getSprite(X)

        val cyclePosition = cycle - 1
        val override = sprite.find { it == cyclePosition }
        if( override != null) {
            CRT[override] = "#"
        }
    }

    // The sprite is 3 pixels wide
    fun getSprite(X: Int): List<Int> {
        return listOf(X - 1, X, X + 1)
    }

    public override fun toString(): String {
        return CRT.joinToString("").windowed(40, 40).joinToString("\n") { it }
    }
}

fun main() {
    val day = "day10"
    val filename = "Day10"

    fun part1(input: List<String>): Int {
        val cpu = CPU()
        input.forEach {cpu.doInstruction(it)}
        return cpu.result()
    }

    fun part2(input: List<String>) {
        val cpu = CPU()
        input.forEach { cpu.doInstruction(it) }
        println(cpu.sprite)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    val partOneTest = part1(testInput)
    check(partOneTest == 13140)

    println("Part one: ${part1(input)}")

    val partTwoTest = part2(testInput)

    println("Part two: ${part2(input)}")
}
