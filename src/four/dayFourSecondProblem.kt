package four

import java.io.File

fun main() {
    var accessibleRolls = 0
    var matrix = arrayOf<Array<Char>>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/four/dayFourInput.txt").forEachLine { line ->
        matrix += line.toCharArray().toTypedArray()
    }

    do {
        matrix = cleanAccessibleRolls(matrix)
        matrix = flagAccessibleRolls(matrix)
        accessibleRolls += countAccessibleRolls(matrix)
    } while (countAccessibleRolls(matrix) > 0)

    println(matrix.joinToString("\n") { it.joinToString("") })

    println(accessibleRolls)
}

fun flagAccessibleRolls(matrix: Array<Array<Char>>): Array<Array<Char>> {
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
                matrix[i][j] = 'x'
            }
        }
    }
    return matrix
}

fun countAccessibleRolls(matrix: Array<Array<Char>>): Int {
    var accessibleRolls = 0
    for (i in 0..<matrix.size) {
        accessibleRolls += matrix[i].count { roll -> roll == 'x' }
    }
    return accessibleRolls
}

fun cleanAccessibleRolls(matrix: Array<Array<Char>>): Array<Array<Char>> {
    for (i in 0..<matrix.size) {
        for (j in 0..<matrix[i].size) {
            if (matrix[i][j] == 'x') {
                matrix[i][j] = '.'
            }
        }
    }
    return matrix
}