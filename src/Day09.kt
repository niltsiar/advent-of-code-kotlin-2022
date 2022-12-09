import kotlin.math.absoluteValue
import kotlin.math.sign

fun main() {
    fun part1(input: List<String>): Int {
        return parseMoves(input).generateTailMoves().toSet().size
    }

    fun part2(input: List<String>): Int {
        val moves = parseMoves(input)
        var knotMoves = moves
        repeat(9) {
            knotMoves = knotMoves.generateTailMoves()
        }
        return knotMoves.toSet().size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    val testInputB = readInput("Day09B_test")
    check(part2(testInputB) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

private fun parseMoves(input: List<String>): List<Pair<Int, Int>> {
    return buildList {
        var x = 0
        var y = 0
        add(Pair(x, y))
        input.forEach { line ->
            val repetitions = line.drop(2).toInt()
            repeat(repetitions) {
                when (line[0]) {
                    'U' -> y++
                    'D' -> y--
                    'L' -> x--
                    'R' -> x++
                }
                add(Pair(x, y))
            }
        }
    }
}

private fun List<Pair<Int, Int>>.generateTailMoves(): List<Pair<Int, Int>> {
    return runningFold(Pair(0, 0)) { oldPosition, newPosition ->
        val dx = newPosition.first - oldPosition.first
        val dy = newPosition.second - oldPosition.second

        when {
            // If diff is less than one in both directions tail doesn't move
            dx.absoluteValue <= 1 && dy.absoluteValue <= 1 -> oldPosition
            // More vertical distance
            dx.absoluteValue < dy.absoluteValue -> Pair(newPosition.first, newPosition.second - dy.sign)
            // More horizontal distance
            dx.absoluteValue > dy.absoluteValue -> Pair(newPosition.first - dx.sign, newPosition.second)
            else -> Pair(newPosition.first - dx.sign, newPosition.second - dy.sign)
        }
    }
}
