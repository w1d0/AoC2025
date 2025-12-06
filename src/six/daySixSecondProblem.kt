package six

import java.io.File

fun main() {
    val inputRows = mutableListOf<String>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/six/daySixInput.txt").forEachLine { line ->
        inputRows += line
    }

    var totalResult = 0L
    var numbers = mutableListOf<Long>()
    var sign = ""
    for (column in inputRows.first().length - 1 downTo 0) {
        sign = inputRows.last()[column].toString()
        if (inputRows.none { it[column].toString().isNotBlank() }) continue
        numbers.add(inputRows.map { it[column].toString() }.dropLast(1).joinToString("").trim().toLong())
        if (sign.isNotBlank()) {
            var columnTotal = when (sign) {
                "*" -> 1L
                else -> 0L
            }
            for (number in numbers) {
                columnTotal = when (sign) {
                    "*" -> columnTotal * number
                    else -> columnTotal + number
                }
            }
            totalResult += columnTotal
            numbers.clear()
        }
    }

    println(totalResult)
}