fun main() {
    fun part1(input: List<String>): Int {
        return input.getPairsOfRanges()
            .map { ranges ->
                when {
                    ranges.first.first in ranges.second && ranges.first.last in ranges.second -> 1
                    ranges.second.first in ranges.first && ranges.second.last in ranges.first -> 1
                    else -> 0
                }
            }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.getPairsOfRanges()
            .count { ranges ->
                ranges.first overlaps ranges.second
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun List<String>.getPairsOfRanges(): List<Pair<IntRange, IntRange>> {
    return map { line ->
        val ranges = line.split(',')
            .map { section ->
                section.split('-')
            }
            .map { limit ->
                limit[0].toInt()..limit[1].toInt()
            }
        ranges[0] to ranges[1]
    }
}

private infix fun IntRange.overlaps(other: IntRange): Boolean {
    return first <= other.last && other.first <= last
}
