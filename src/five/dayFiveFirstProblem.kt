package five

import java.io.File

fun main() {
    var freshIngredients = 0
    val freshIds = mutableListOf<Range>()
    val availableIds = mutableListOf<Long>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/five/dayFiveInput.txt").forEachLine { line ->
        if (line.contains("-")) {
            val (min, max) = line.split("-").map { it.toLong() }
            freshIds += Range(min, max)
        } else if (line.isNotEmpty()) {
            availableIds += line.toLong()
        }
    }

    availableIds.forEach outer@{ id ->
        freshIds.forEach { range ->
            if (range.contains(id)) {
                freshIngredients++
                return@outer
            }
        }
    }

    println(freshIngredients)
}

class Range(val min: Long, val max: Long) {
    fun contains(id: Long) = id in min..max
}