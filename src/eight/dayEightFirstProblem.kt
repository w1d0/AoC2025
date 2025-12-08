package eight

import java.io.File
import kotlin.math.sqrt

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
    do {
        val currentDistance = distances[iteration]
        if (currentDistance.originPoint.circuit != currentDistance.destPoint.circuit) {
            val originalDestCircuit = currentDistance.destPoint.circuit!!
            circuits[currentDistance.originPoint.circuit!!] += circuits[currentDistance.destPoint.circuit!!]
            circuits[currentDistance.destPoint.circuit!!].forEach { junctionBox -> junctionBox.circuit = currentDistance.originPoint.circuit }
            circuits[originalDestCircuit] = emptySet()
            connectionsMade++
        } else if (currentDistance.originPoint.circuit == currentDistance.destPoint.circuit){
            connectionsMade++
        }
        iteration++
    } while (connectionsMade < 1000)

    val multipliedSizes = circuits.filter { it.isNotEmpty() }.sortedBy { it.size }.reversed().let {
        it[0].size * it[1].size * it[2].size
    }

    println(multipliedSizes)
}

data class JunctionBox(
    val x: Double,
    val y: Double,
    val z: Double,
    var circuit: Int? = null,
)

data class Distance(
    val originPoint: JunctionBox,
    val destPoint: JunctionBox,
    val distance: Double,
)

fun distanceBetween(originPoint: JunctionBox, destPoint: JunctionBox) =
    sqrt(
        (originPoint.x - destPoint.x) * (originPoint.x - destPoint.x)
        + (originPoint.y - destPoint.y) * (originPoint.y - destPoint.y)
        + (originPoint.z - destPoint.z) * (originPoint.z - destPoint.z)
    )
