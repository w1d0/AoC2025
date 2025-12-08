package eight

import java.io.File

fun main() {
    val junctionBoxes = mutableListOf<JunctionBox>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/eight/dayEightInput.txt").forEachLine { line ->
        val coordinates = line.split(",").map { it.toDouble() }
        junctionBoxes += JunctionBox(coordinates[0], coordinates[1], coordinates[2])
    }

    var distances = mutableListOf<Distance>()

    junctionBoxes.forEachIndexed { index, origin ->
        junctionBoxes.subList(index + 1, junctionBoxes.size).forEach { destination ->
            if (origin != destination) {
                distances += Distance(origin, destination, distanceBetween(origin, destination))
            }
        }
    }

    val circuits = mutableListOf<Set<JunctionBox>>()
    junctionBoxes.forEach { it ->
        circuits += setOf(it)
        it.circuit = circuits.lastIndex
    }
    distances = distances.sortedBy { it.distance }.toMutableList()

    var iteration = 0
    var connectionsMade = 0
    var productLastX = 0.0
    do {
        val currentDistance = distances[iteration]
        if (currentDistance.originPoint.circuit != currentDistance.destPoint.circuit) {
            val originalDestCircuit = currentDistance.destPoint.circuit!!
            circuits[currentDistance.originPoint.circuit!!] += circuits[currentDistance.destPoint.circuit!!]
            circuits[currentDistance.destPoint.circuit!!].forEach { junctionBox -> junctionBox.circuit = currentDistance.originPoint.circuit }
            circuits[originalDestCircuit] = emptySet()
            productLastX = currentDistance.originPoint.x * currentDistance.destPoint.x
            connectionsMade++
        } else if (currentDistance.originPoint.circuit == currentDistance.destPoint.circuit){
            connectionsMade++
        }
        iteration++
    } while (circuits[currentDistance.originPoint.circuit!!].size < 1000)

    println(productLastX.toBigDecimal().toPlainString())
}
