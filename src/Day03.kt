fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { rucksack ->
            val first = rucksack.substring(0 until rucksack.length / 2)
            val second = rucksack.substring(rucksack.length / 2)
            val common = first.toSet() intersect second.toSet()
            common.single().priority
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3)
            .sumOf { elves ->
                elves.map { it.toSet() }
                    .reduce { acc, elf ->
                        acc.toSet() intersect elf.toSet()
                    }
                    .single()
                    .priority
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

val Char.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("bad input")
    }
