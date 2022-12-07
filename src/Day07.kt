fun main() {
    fun part1(input: List<String>): Int {
        val fileSystem = parseFileSystem(input)
        return fileSystem.findNodes { node ->
            node is Directory && node.size <= 100_000
        }.sumOf { node -> node.size }

    }

    fun part2(input: List<String>): Int {
        val fileSystem = parseFileSystem(input)
        val unusedSpace = 70_000_000 - fileSystem.size
        val neededSpace = 30_000_000 - unusedSpace

        return fileSystem
            .findNodes { node ->
                node is Directory && node.size >= neededSpace
            }.minBy { node -> node.size }
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

sealed interface Node {
    val name: String
    val size: Int
    val parent: Node?
}

class Directory(
    override val name: String,
    override val parent: Directory?,
    val nodes: MutableList<Node> = mutableListOf(),
) : Node {

    override val size: Int
        get() = nodes.sumOf { node -> node.size }
}

class File(
    override val name: String,
    override val size: Int,
    override val parent: Node?,
) : Node

fun parseFileSystem(input: List<String>): Directory {
    val root = Directory("/", null)

    parseFileSystem(input, root, root)

    return root
}

tailrec fun parseFileSystem(input: List<String>, currentDirectory: Directory, root: Directory) {
    if (input.isEmpty()) {
        return
    }

    val line = input.first()
    val newDirectory = if (line.first() == '$') {
        val command = line.split(' ').drop(1)
        when (command[0]) {
            "cd" -> {
                when (val destinationName = command[1]) {
                    ".." -> currentDirectory.parent ?: root
                    "/" -> root
                    else -> {
                        val destinationDirectory = currentDirectory.nodes
                            .filterIsInstance<Directory>()
                            .single { it.name == destinationName }
                        destinationDirectory
                    }
                }
            }

            "ls" -> currentDirectory
            else -> error("BAD INPUT")
        }
    } else {
        val output = line.split(" ")
        when (output[0]) {
            "dir" -> currentDirectory.nodes.add(Directory(output[1], currentDirectory))
            else -> currentDirectory.nodes.add(File(output[1], output[0].toInt(), currentDirectory))
        }
        currentDirectory
    }

    parseFileSystem(input.drop(1), newDirectory, root)
}

fun Node.findNodes(predicate: (Node) -> Boolean): List<Node> {
    return if (predicate(this)) {
        listOf(this)
    } else {
        emptyList()
    } + if (this is Directory) {
        nodes.flatMap { node -> node.findNodes(predicate) }
    } else {
        emptyList()
    }
}
