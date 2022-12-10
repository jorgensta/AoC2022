import day7.Dir
import day7.File

fun String.isTerminalInstruction() = this.contains("$")
fun String.isChangeDirectory() = this.contains("cd") && this.isTerminalInstruction()
fun String.isListDir() = this.contains("ls") && this.isTerminalInstruction()
fun String.goesUpOneDirectory() = this.isChangeDirectory() && this.split(" ").last() == ".."
fun String.goesIntoDirectory() = this.isChangeDirectory() && this.contains("..").not()
fun String.dirName() = this.split(" ").last()
fun String.isFile() = this.split(" ").first().all { it.isDigit() }
fun String.fileSize() = this.split(" ").first().toInt()
fun String.fileName() = this.split(" ").last()
fun String.isDir() = this.split(" ").first() == "dir"
fun String.isRootNode() = this.isChangeDirectory() && this.split(" ").last() == "/"


fun main() {
    val day = "day7"
    val filename = "Day07"

    val rootDir = Dir("/")

    fun constructFilesystem(input: List<String>) {
        var currentDir = rootDir

        input.forEach { line ->
            if (line.isListDir()) return@forEach
            if (line.isRootNode()) {
                currentDir = rootDir
                return@forEach
            }

            if (line.goesIntoDirectory()) {
                try {
                    currentDir = currentDir.children.find { it.name == line.dirName() }!!
                } catch (ex: Exception) {
                    println("Could not find child")
                }
                return@forEach
            }

            if (line.goesUpOneDirectory()) {
                if (currentDir.parent != null) {
                    currentDir = currentDir.parent!!
                    return@forEach
                }
            }

            if (line.isDir()) {
                val newDirectory = Dir(line.dirName())
                currentDir.children.add(newDirectory)
                newDirectory.parent = currentDir
                return@forEach
            }

            if(line.isFile()) {
                currentDir.files.add(File(line.fileSize(), line.fileName()))
                return@forEach
            }
        }
    }

    fun browse(dir: Dir, collector: (Dir) -> Unit) {
        if (dir.children.isEmpty()) return

        dir.children.forEach {
            collector(it)
            browse(it, collector)
        }
    }

    fun sumAllDirectoriesOfSizeAtMost(size: Int): Int {
        var result = 0
        val collector: (Dir) -> Unit = { dir: Dir ->
            val dirSize = dir.getDirSize()
            if(dirSize <= size) {
                result += dirSize
            }
        }
        browse(rootDir, collector)
        return result
    }

    fun findAllDirs(): List<Dir> {
        val dirs = mutableListOf<Dir>()
        val collector: (Dir) -> Unit = { dir: Dir -> dirs.add(dir)}
        browse(rootDir, collector)
        return dirs.toList()
    }

    fun findSizeOfDirectoryToDelete(input: List<String>): Int? {
        constructFilesystem(input)

        val sizeAllDirs = rootDir.getDirSize()
        val totalDiskSpace = 70000000
        val atleastUnusedSpace = 30000000
        val unusedSpace = totalDiskSpace - sizeAllDirs

        val dirs = findAllDirs().map { it.getDirSize() }.sorted()
        return dirs.find { dirSize -> (unusedSpace + dirSize) >= atleastUnusedSpace }
    }

    fun part1(input: List<String>): Int {
        constructFilesystem(input)
        return sumAllDirectoriesOfSizeAtMost(100000)
    }

    fun part2(input: List<String>): Int? {
        return findSizeOfDirectoryToDelete(input)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/$day/${filename}_test")
    val input = readInput("/$day/$filename")
    // val partOneTest = part1(testInput)
    // check(partOneTest == 95437)

    // println("Part one: ${part1(input)}")

    // val partTwoTest = part2(testInput)
    // check(partTwoTest == 24933642)

    println("Part two: ${part2(input)}")
}
