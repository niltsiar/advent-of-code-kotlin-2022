fun main() {
    fun part1(input: List<String>): String {
        val stacks = processStacks(input[0])
        val instructions = processInstructions(input[1])

        instructions.forEach { instruction ->
            val number = instruction.first
            val from = instruction.second - 1
            val to = instruction.third - 1

            for (i in 1..number) {
                val crate = stacks[from].removeFirst()
                stacks[to].add(0, crate)
            }
        }

        return stacks.joinToString("") { it.first().toString() }
    }

    fun part2(input: List<String>): String {
        val stacks = processStacks(input[0])
        val instructions = processInstructions(input[1])

        instructions.forEach { instruction ->
            val number = instruction.first
            val from = instruction.second - 1
            val to = instruction.third - 1

            val crates = stacks[from].take(number)
            for (i in 1..number) {
                stacks[from].removeFirst()
            }
            stacks[to].addAll(0, crates)
        }

        return stacks.joinToString("") { it.first().toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test", "\n\n")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05", "\n\n")
    println(part1(input))
    println(part2(input))
}

private fun processStacks(input: String): List<MutableList<Char>> {
    val cratesPerLine = input
        .split('\n')
        .dropLast(1)
        .map { it.chunked(4) }

    val numStacks = cratesPerLine.first().size
    val stacks = List(numStacks) { mutableListOf<Char>() }

    cratesPerLine.forEach { line ->
        line.forEachIndexed { index, s ->
            if (s[1].isLetter()) {
                stacks[index].add(s[1])
            }
        }
    }

    return stacks
}

private fun processInstructions(input: String): List<Triple<Int, Int, Int>> {
    val instructionRegex = Regex("move (\\d+) from (\\d+) to (\\d+)")
    return input.split('\n')
        .filter { line -> line.isNotBlank() }
        .map { line ->
            val match = instructionRegex.matchEntire(line) ?: error("BAD INPUT")
            Triple(match.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt())
        }
}
