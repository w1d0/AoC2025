package one

import java.io.File

fun main() {
    var zerosEncountered = 0
    var currentDialValue = 50
    var previousDialValue = currentDialValue

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/one/dayOneInput.txt").forEachLine { line ->
        var clicks = line.substring(1, line.length).toInt()
        zerosEncountered += (clicks / 100)
        previousDialValue = currentDialValue
        clicks %= 100
        if (clicks != 0) {
            when (line.first()) {
                'L' -> currentDialValue -= clicks
                'R' -> currentDialValue += clicks
            }
            if (currentDialValue < 0) {
                currentDialValue += 100
                if (previousDialValue != 0) zerosEncountered++
            }
            if (currentDialValue > 99) {
                currentDialValue %= 100
                if (currentDialValue != 0) zerosEncountered++
            }
            if (currentDialValue == 0) zerosEncountered++
        }
    }

    println(zerosEncountered)
}