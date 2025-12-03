package three

import java.io.File

fun main() {
    val batteries = File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/three/dayThreeInput.txt").readLines()
    val maximumJoltages = mutableListOf<Int>()

    batteries.forEach { battery ->
        var maximumJoltage = 0
        for (firstCharIndex in 0..battery.length - 2) {
            val currentFirstChar = battery[firstCharIndex].toString()
            for (secondCharIndex in firstCharIndex + 1..<battery.length) {
                val currentSecondChar = battery[secondCharIndex].toString()
                maximumJoltage = maxOf(maximumJoltage, (currentFirstChar+currentSecondChar).toInt())
            }
        }
        maximumJoltages.add(maximumJoltage)
    }

    println(maximumJoltages.sum())
}