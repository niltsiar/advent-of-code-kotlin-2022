fun main() {
    fun part1(input: List<String>): Int {
        val inputs = parseInput(input)
        val interestingCycles = listOf(20, 60, 100, 140, 180, 220)
        return interestingCycles.sumOf { cycle -> cycle * calculateRegisterValue(inputs, cycle) }
    }

    fun part2(input: List<String>) {
        val inputs = parseInput(input)
        val s = StringBuilder()
        for (cycle in 1..240) {
            val registerValue = calculateRegisterValue(inputs, cycle)
            val pixelPosition = (cycle % 40) - 1
            if (pixelPosition in (registerValue - 1)..(registerValue + 1)) {
                s.append("#")
            } else {
                s.append(".")
            }
        }
        s.chunked(40)
            .forEach { println(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}

private fun parseInput(input: List<String>): List<Int> {
    return input.flatMap { line ->
        val entries = line.split(' ')
        when (entries[0]) {
            "noop" -> listOf(0)
            "addx" -> listOf(0, entries[1].toInt())
            else -> emptyList()
        }
    }
}

private fun calculateRegisterValue(inputs: List<Int>, cycle: Int): Int {
    return 1 + inputs.take(cycle).sum() - inputs[cycle - 1]
}
