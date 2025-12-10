package nine

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val redTiles = mutableListOf<Tile>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/nine/dayNineInput.txt").forEachLine { line ->
        val coordinates = line.split(",").map { it.toInt() }
        redTiles += Tile(coordinates[0], coordinates[1])
    }

    var biggestSurface = 0L

    for (i in redTiles.indices) {
        for (j in i + 1 until redTiles.size) {
            biggestSurface = max(biggestSurface, surface(redTiles[i], redTiles[j]))
        }
    }

    println(biggestSurface)
}

data class Tile(
    val x: Int,
    val y: Int,
)

fun surface(first: Tile, second: Tile) =
    (max(first.x, second.x) - min(first.x, second.x) + 1).toLong() * (max(first.y, second.y) - min(first.y, second.y) + 1).toLong()
