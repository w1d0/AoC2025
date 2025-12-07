package seven

import java.io.File

//fun main() {
//    val inputRows = mutableListOf<String>()
//
//    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/seven/daySevenInputTest.txt").forEachLine { line ->
//        inputRows += line
//    }
//
//    val totalTimelines = Counter()
//
//    val indexOfStartingPoint = inputRows.first().indexOf("S")
//    route(1, indexOfStartingPoint, inputRows, totalTimelines)
//
//    println(totalTimelines.value)
//}
//
//fun route(startingRow: Int, startingColumn: Int, inputRows: List<String>, counter: Counter) {
//    if (startingRow == inputRows.size -1) {
//        counter.value++
//        return
//    }
//    if (inputRows[startingRow][startingColumn] == '^') {
//        route(startingRow + 1, startingColumn - 1, inputRows, counter)
//        route(startingRow + 1, startingColumn + 1, inputRows, counter)
//    } else {
//        route(startingRow + 1, startingColumn, inputRows, counter)
//    }
//}

//fun main() {
//    val inputRows = mutableListOf<String>()
//
//    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/seven/daySevenInput.txt").forEachLine { line ->
//        inputRows += line
//    }
//
//    var beams = mutableListOf<Int>()
//
//    beams.add(inputRows.first().indexOf("S"))
//    for (rowIndex in 1..<inputRows.size) {
//        val row = inputRows[rowIndex]
//        val newBeams = mutableListOf<Int>()
//        beams.forEach { beam ->
//            if (row[beam] == '.') newBeams.add(beam)
//            if (row[beam] == '^') newBeams.addAll(listOf(beam - 1, beam + 1))
//        }
//        beams = newBeams
//        println("At row $rowIndex, there are ${beams.size}")
//    }
//
//    println(beams.size)
//}

fun main() {
    val inputRows = mutableListOf<String>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/seven/daySevenInput.txt").forEachLine { line ->
        inputRows += line
    }

    var beams = List(inputRows.first().length) { _ -> 0L }.toMutableList()

    beams[inputRows.first().indexOf("S")] = beams[inputRows.first().indexOf("S")] + 1
    for (rowIndex in 1..<inputRows.size) {
        val row = inputRows[rowIndex]
        beams.forEachIndexed { index, beam ->
            if (beam == 0L) return@forEachIndexed
            if (row[index] == '^') {
                beams[index-1] = beams[index-1] + beams[index]
                beams[index+1] = beams[index+1] + beams[index]
                beams[index] = 0
            }
        }
        println("At row $rowIndex, there are ${beams.sum()}")
    }

    println(beams.sum())
}

data class Counter(var value: Long = 0L)