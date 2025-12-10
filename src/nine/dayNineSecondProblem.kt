package nine

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val redTiles = mutableListOf<Tile>()
    val availableTiles = mutableMapOf<Int,IntRange>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/nine/dayNineInput.txt").forEachLine { line ->
        val coordinates = line.split(",").map { it.toInt() }
        redTiles += Tile(coordinates[0], coordinates[1])
    }

    for (i in redTiles.indices) {
        val currentTile = redTiles[i]
        val nextTile = redTiles[(i+1)%redTiles.size]
        if (currentTile.y == nextTile.y) {
            if (availableTiles[currentTile.y] == null) {
                availableTiles[currentTile.y] = min(currentTile.x,nextTile.x)..max(currentTile.x,nextTile.x)
            } else {
                val range = availableTiles[currentTile.y]!!
                availableTiles[currentTile.y] = min(range.first, min(currentTile.x,nextTile.x))..max(range.last, max(currentTile.x,nextTile.x))
            }
        } else {
            val x = currentTile.x
            for (y in min(currentTile.y, nextTile.y)..max(currentTile.y, nextTile.y)) {
                if (availableTiles[y] == null) {
                    availableTiles[y] = x..x
                } else {
                    val range = availableTiles[y]!!
                    availableTiles[y] = min(range.first, x)..max(range.last, x)
                }
            }
        }
    }

    var biggestSurface = 0L

    println("Size: ${redTiles.size}")
    for (i in redTiles.indices) {
        println("Tile: $i")
        for (j in i + 1 until redTiles.size) {
            println("Next tile: $j")
            val currentTile = redTiles[i]
            val nextCorner = redTiles[j]

            val leftBorder = min(currentTile.x, nextCorner.x)
            val rightBorder = max(currentTile.x, nextCorner.x)
            val topBorder = min(currentTile.y, nextCorner.y)
            val bottomBorder = max(currentTile.y, nextCorner.y)

            var isInside = true
            for (y in topBorder..bottomBorder) {
                if (!isInside) break
                isInside = isInside(availableTiles, leftBorder, y) && isInside(availableTiles, rightBorder, y)
            }

            if (
                isInside
            ) {
                biggestSurface = max(biggestSurface, surface(currentTile, nextCorner))
            }
        }
    }

    println(biggestSurface)
}

fun isInside(availableTiles: Map<Int,IntRange>, x: Int, y: Int) = availableTiles[y]?.contains(x) ?: false
