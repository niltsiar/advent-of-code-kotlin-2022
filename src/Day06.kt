import java.io.File

fun main() {
    fun part1(input: String): Int {
        return findIndexOfMarker(input, 4)
    }

    fun part2(input: String): Int {
        return findIndexOfMarker(input, 14)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = File("src", "Day06_test.txt").readText()
    check(part1(testInput) == 11)
    check(part2(testInput) == 26)

    val input = File("src", "Day06.txt").readText()
    println(part1(input))
    println(part2(input))
}

private fun findIndexOfMarker(input: String, markerSize: Int): Int {
    var set = mutableSetOf<Char>()
    input.forEachIndexed { index, c ->
        if (set.contains(c)) {
            set = set.dropWhile { it != c }.toMutableSet()
            set.remove(c)
            set.add(c)
        } else {
            set.add(c)
            if (set.size == markerSize) {
                return index + 1
            }
        }
    }
    error("BAD INPUT")
}
