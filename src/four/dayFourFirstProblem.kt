package four

import java.io.File

fun main() {
    var accessibleRolls = 0
    var matrix = arrayOf<Array<Char>>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/four/dayFourInput.txt").forEachLine { line ->
        matrix += line.toCharArray().toTypedArray()
    }

    val lineSize = matrix[0].size
    for (i in 0..<matrix.size) {
        for (j in 0..<lineSize) {
            if (matrix[i][j] == '.') continue
            var adjacentRolls = 0
            matrix.getOrNull(i-1)?.getOrNull(j-1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i-1)?.getOrNull(j)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i-1)?.getOrNull(j+1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i)?.getOrNull(j-1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i)?.getOrNull(j+1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i+1)?.getOrNull(j-1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i+1)?.getOrNull(j)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }
            matrix.getOrNull(i+1)?.getOrNull(j+1)?.let { roll -> if (roll == '@' || roll == 'x') adjacentRolls++ }

            if (adjacentRolls < 4) {
                accessibleRolls++
                matrix[i][j] = 'x'
            }
        }
    }

    println(matrix.joinToString("\n") { it.joinToString("") })

    println(accessibleRolls)
}