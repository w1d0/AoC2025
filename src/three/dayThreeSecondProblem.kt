package three

import java.io.File

fun main() {
    val batteries = File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/three/dayThreeInput.txt").readLines()
    val maximumJoltages = mutableListOf<Long>()

    batteries.forEach { battery ->
        var nextIndexStart = 0
        var currentJoltage = ""
        for (remainingDigits in 11 downTo 0) {
            battery.substring(nextIndexStart, battery.length - remainingDigits).let { substring ->
                val nextMax = substring.toCharArray().maxOfOrNull { char -> char.digitToInt() }
                currentJoltage += nextMax ?: 0
                nextIndexStart += substring.indexOfFirst { char -> char.digitToInt() == nextMax } + 1
            }
        }
        maximumJoltages.add(currentJoltage.toLong())
    }

    println(maximumJoltages.sum())
}