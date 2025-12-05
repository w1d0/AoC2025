package five

import java.io.File

fun main() {
    var theoricFreshIds = 0L
    var availableFreshIds = 0L
    val freshIds = mutableListOf<RangeSecond>()
    val filteredFreshIds = mutableListOf<RangeSecond>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/five/dayFiveInput.txt").forEachLine { line ->
        if (line.contains("-")) {
            val (min, max) = line.split("-").map { it.toLong() }
            freshIds += RangeSecond(min, max)
        }
    }

    freshIds.sortBy { it.min }
    freshIds.forEach { range ->
        if (filteredFreshIds.any { it.encloses(range) }) return@forEach

        var newRange = range
        filteredFreshIds.forEach { filteredRange ->
            newRange = newRange.reduce(filteredRange)
        }
        if (newRange.min <= newRange.max) {
            filteredFreshIds += newRange
        }
    }

    freshIds.forEach { theoricFreshIds += it.max - it.min + 1 }
    filteredFreshIds.forEach { availableFreshIds += it.max - it.min + 1 }

    println(freshIds.sortedBy { it.min }.joinToString("\n") { "${it.min} - ${it.max}" })
//    println(freshIds.joinToString("\n") { "${it.min} - ${it.max}: ${it.max - it.min + 1}" })
    println(theoricFreshIds)
    // 435177989437182
    println("-----------------")
    println(filteredFreshIds.joinToString("\n") { "${it.min} - ${it.max}: ${it.max - it.min + 1}" })
    println(availableFreshIds)
    // 341473546698116
}

class RangeSecond(val min: Long, val max: Long) {
    fun contains(id: Long) = id in min..max
    fun encloses(range: RangeSecond) = contains(range.min) && contains(range.max)
    fun overlaps(range: RangeSecond) = contains(range.min) || contains(range.max)
    fun reduce(range: RangeSecond): RangeSecond {
        return if (this.overlaps(range)) {
            if (min in range.min..range.max) {
                RangeSecond(range.max + 1, max)
            } else {
                RangeSecond(min, range.min - 1)
            }
        } else {
            RangeSecond(min, max)
        }
    }
}