package seven

import java.io.File

fun main() {
    val inputRows = mutableListOf<String>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/seven/daySevenInput.txt").forEachLine { line ->
        inputRows += line
    }

    var totalSplits = 0
    val beams = mutableSetOf<Int>()

    beams.add(inputRows.first().indexOf("S"))
    for (rowIndex in 1..<inputRows.size) {
        val row = inputRows[rowIndex]
        if (row.contains("^")) {
            row.indexesOf('^').forEach {
                if (beams.contains(it)) {
                    totalSplits++
                    beams.remove(it)
                    beams.add(it - 1)
                    beams.add(it + 1)
                }
            }
        }
    }

    println(totalSplits)
}

fun String.indexesOf(search: Char) = this.mapIndexedNotNull { index, char -> index.takeIf { char == search } }
