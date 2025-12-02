package one

import java.io.File

fun main() {
    var zerosEncountered = 0

    var currentDialValue = 50

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/one/dayOneInput.txt").forEachLine { line ->
        val clicks = line.substring(1, line.length).toInt() % 100
        when (line.first()) {
            'L' -> currentDialValue -= clicks
            'R' -> currentDialValue += clicks
        }
        if (currentDialValue < 0) currentDialValue += 100
        if (currentDialValue > 99) currentDialValue %= 100
        if (currentDialValue == 0) zerosEncountered++
    }

    println(zerosEncountered)
}