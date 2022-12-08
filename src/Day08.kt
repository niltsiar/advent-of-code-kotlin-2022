fun main() {
    fun part1(input: List<String>): Int {
        return input.indices.sumOf { i ->
            input.first().indices.count { j ->
                createListOfTrees(i, j, input.size, input.first().length).any { trees ->
                    trees.all { (x, y) -> input[x][y] < input[i][j] }
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.indices.maxOf { i ->
            input.first().indices.maxOf { j ->
                createListOfTrees(i, j, input.size, input.first().length).map { trees ->
                    minOf(trees.takeWhile { (x, y) -> input[x][y] < input[i][j] }.size + 1, trees.size)
                }.reduce(Int::times)
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun createListOfTrees(i: Int, j: Int, maxRows: Int, maxColumns: Int): List<List<Pair<Int, Int>>> {
    val left = (j - 1 downTo 0).map { i to it }
    val right = (j + 1 until maxColumns).map { i to it }
    val up = (i - 1 downTo 0).map { it to j }
    val down = (i + 1 until maxRows).map { it to j }

    return listOf(left, right, up, down)
}
