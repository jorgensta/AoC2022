package day7

class Dir(val name: String) {
    var parent: Dir? = null
    val files: MutableList<File> = mutableListOf()
    val children: MutableList<Dir> = mutableListOf()

    fun getDirSize(): Int {
        return files.sumOf { it.size } + children.sumOf { it.getDirSize() }
    }
}