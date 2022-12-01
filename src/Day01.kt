fun main() {
    fun part1(input: List<String>): Int {
        return calculateCaloriesCarriedByElf(input)
            .max()
    }

    fun part2(input: List<String>): Int {
        return calculateCaloriesCarriedByElf(input)
            .sortedDescending()
            .take(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun calculateCaloriesCarriedByElf(input: List<String>): List<Int> {
    return input.fold(listOf(0)) { acc, s ->
        if (s.isBlank()) {
            acc + 0
        } else {
            val newValueForAcc = acc.last() + s.toInt()
            acc.dropLast(1) + newValueForAcc
        }
    }
}
