package day7

class Dir(val name: String) {
    var parent: Dir? = null
    val files: MutableList<File> = mutableListOf()
    val children: MutableList<Dir> = mutableListOf()

    fun getDirSize(): Int {
        var size = files.sumOf { it.size }
        val collector: (Dir) -> Unit = { dir: Dir -> size += dir.files.sumOf { it.size } }
        browse(this.children, collector)
        return size
    }

    private fun browse(children: List<Dir>, collector: (Dir) -> Unit) {
        if (children.isEmpty()) return
        children.forEach {
            collector(it)
            browse(it.children, collector)
        }
    }
}