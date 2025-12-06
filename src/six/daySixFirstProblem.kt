package six

import java.io.File

fun main() {
    val inputRows = mutableListOf<List<String>>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/six/daySixInput.txt").forEachLine { line ->
        inputRows += line
            .replace("\\s{2,}".toRegex(), " ")
            .trim()
            .split(" ")
            .map { it.trim() }
    }

    val rows = inputRows.size
    val columns = inputRows.first().size

    var totalResult = 0L

    for (column in 0..<columns) {
        val sign = inputRows.last()[column]
        var columnTotal = when (sign) {
            "*" -> 1L
            else -> 0L
        }
        for (row in 0..<rows - 1) {
            columnTotal = when (sign) {
                "*" -> columnTotal * inputRows[row][column].toLong()
                else -> columnTotal + inputRows[row][column].toLong()
            }
        }
        totalResult += columnTotal
    }

    println(totalResult)
}
