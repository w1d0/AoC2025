package two

import java.io.File

fun main() {
    val ranges = File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/two/dayTwoInput.txt").readLines().first().split(",")
    val invalidIds = mutableListOf<Long>()

    ranges.forEach { range ->
        val (min, max) = range.split("-").map { it.toLong() }
        for (id in min..max) {
            val textualId = id.toString()
            for (nbChar in 1..(textualId.length / 2)) {
                val pattern = textualId.take(nbChar)
                if (Regex("($pattern){2}").matches(textualId)) invalidIds.add(id)
            }
        }
    }

    println(invalidIds.sum())
}