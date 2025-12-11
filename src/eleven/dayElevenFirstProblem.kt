package eleven

import java.io.File

fun main() {
    val devices = mutableListOf<Device>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/eleven/dayElevenInput.txt").forEachLine { line ->
        val chunks = line.split(" ")
        devices += Device(
            name = chunks[0].dropLast(1),
            pathsTo = chunks.drop(1)
        )
    }

    var paths = mutableListOf<String>()

    val start = devices.first { it.name == "you" }

    paths += walkPaths(start, "you", devices)

    println(devices)
    println(paths.filter { it.isNotBlank() }.distinct().size)
}

data class Device(
    val name: String,
    val pathsTo: List<String>,
)

fun walkPaths(device: Device, currentPath: String, devices: List<Device>): List<String> {
    if (device.pathsTo.contains("out")) {
        return listOf("$currentPath-out")
    }

    val paths = mutableListOf<String>()
    device.pathsTo.forEach { path ->
        if (!currentPath.contains(path)) {
            paths += walkPaths(devices.first { it.name == path }, "$currentPath-$path", devices)
        }
    }
    return paths
}
