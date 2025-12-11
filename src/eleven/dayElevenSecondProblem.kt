package eleven

import com.sun.media.sound.EmergencySoundbank.fft
import java.io.File
import kotlin.collections.List
import kotlin.collections.distinct
import kotlin.collections.drop
import kotlin.collections.filter
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.listOf
import kotlin.collections.mutableListOf
import kotlin.collections.plusAssign

//fun main() {
//    val devices = mutableListOf<Device>()
//
//    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/eleven/dayElevenInput.txt").forEachLine { line ->
//        val chunks = line.split(" ")
//        devices += Device(
//            name = chunks[0].dropLast(1),
//            pathsTo = chunks.drop(1)
//        )
//    }
//
//    val dacToOut = mutableListOf<String>()
//    dacToOut += walkPathsSvr(devices.first { it.name == "dac" }, "dac", devices, "out", avoid = listOf("fft", "svr"))
//    println("dac to out: ${dacToOut.distinct().size}")
//
//    val fftToDac = mutableListOf<String>()
//    fftToDac += walkPathsSvr(devices.first { it.name == "fft" }, "fft", devices, "dac", avoid = listOf("out", "svr").plus(dacToOut.flatMap { it.split("-") }.distinct()))
//    println("fft to dac: ${fftToDac.distinct().size}")
//
//    val svrToFft = mutableListOf<String>()
//    svrToFft += walkPathsSvr(devices.first { it.name == "svr" }, "svr", devices, "fft", avoid = listOf("out", "dac").plus(dacToOut.flatMap { it.split("-") }.distinct()).plus(fftToDac.flatMap { it.split("-") }.distinct()))
//    println("svr to fft: ${svrToFft.distinct().size}")
//
//    println(svrToFft.distinct().size * fftToDac.distinct().size * dacToOut.distinct().size)
//}
//
//fun walkPathsSvr(device: Device, currentPath: String, devices: List<Device>, target: String, avoid: List<String>): List<String> {
//    println("$currentPath to $target")
//    if (device.pathsTo.contains(target)) {
//        return listOf("$currentPath-$target")
//    }
//
//    val paths = mutableListOf<String>()
//    device.pathsTo.forEach { path ->
//        if (!avoid.contains(path) && !currentPath.contains(path)) {
//            paths += walkPathsSvr(devices.first { it.name == path }, "$currentPath-$path", devices, target, avoid)
//        }
//    }
//    return paths
//}

fun main() {
    val devices = mutableListOf<EnrichedDevice>()

    File("/Users/w1d0/worksspaces/BearIT/AdventOfCode2025/src/eleven/dayElevenInput.txt").forEachLine { line ->
        val chunks = line.split(" ")
        devices += EnrichedDevice(
            name = chunks[0].dropLast(1),
            pathsTo = chunks.drop(1)
        )
    }

    println("Read file: ${devices.size} devices")

    devices.forEach { it.linkDevices(devices) }

    println("Linked devices: ${devices.filter { it.linkedDevices.isNotEmpty() }.size}")

    devices.forEach { it.hasAccessToDac() }
    devices.forEach { it.hasAccessToFft() }
    devices.forEach { it.hasAccessToOut() }
    println("Has access to out: ${devices.filter { it.canGoToOut == true }.size}")
    println("Has access to dac: ${devices.filter { it.canGoToDac == true }.size}")
    println("Has access to fft: ${devices.filter { it.canGoToFft == true }.size}")

    println("-----------------")
    println(devices.filter { it.canGoToOut == null }.size)
    println(devices.filter { it.canGoToDac == null }.size)
    println(devices.filter { it.canGoToFft == null }.size)

    println("-----------------")
    val svrToFft = walkToFft(devices.first { it.name == "svr" }, "svr", devices, devices.first { it.name == "fft" })
    println(svrToFft.distinct().size)

    println("-----------------")
    val fftToDac = walkToDac(devices.first { it.name == "fft" }, "fft", devices, devices.first { it.name == "dac" })
    println(fftToDac.distinct().size)

    println("-----------------")
    val dacToOut = walkToOut(devices.first { it.name == "dac" }, "dac", devices, "out")
    println(dacToOut.distinct().size)

    println(dacToOut.distinct().size.toLong() * fftToDac.distinct().size.toLong() * svrToFft.distinct().size.toLong())
}

data class EnrichedDevice(
    val name: String,
    val pathsTo: List<String>,
    val linkedDevices: MutableList<EnrichedDevice> = mutableListOf(),
    var canGoToDac: Boolean? = null,
    var canGoToFft: Boolean? = null,
    var canGoToOut: Boolean? = null,
) {
    fun linkDevices(devices: List<EnrichedDevice>) {
        pathsTo.forEach { path ->
            if (path == "dac") canGoToDac = true
            else if (path == "fft") canGoToFft = true
            else if (path == "out") canGoToOut = true
            else linkedDevices += devices.first { it.name == path }
        }
    }

    fun hasAccessToDac(): Boolean {
        if (canGoToDac != null) return canGoToDac == true
        canGoToDac = linkedDevices.any { it.hasAccessToDac() == true }
        return canGoToDac == true
    }

    fun hasAccessToFft(): Boolean {
        if (canGoToFft != null) return canGoToFft == true
        canGoToFft = linkedDevices.any { it.hasAccessToFft() == true }
        return canGoToFft == true
    }

    fun hasAccessToOut(): Boolean {
        if (canGoToOut != null) return canGoToOut == true
        canGoToOut = linkedDevices.any { it.hasAccessToOut() == true }
        return canGoToOut == true
    }
}

fun walkToFft(device: EnrichedDevice, currentPath: String, devices: List<EnrichedDevice>, target: EnrichedDevice): List<String> {
    if (device.pathsTo.contains(target.name)) {
        return listOf("$currentPath-${target.name}")
    }

    val paths = mutableListOf<String>()
    device.pathsTo.forEach { path ->
        if (!currentPath.contains(path) && devices.first { it.name == path }.canGoToFft == true) {
            paths += walkToFft(devices.first { it.name == path }, "$currentPath-$path", devices, target)
        }
    }
    return paths
}

fun walkToDac(device: EnrichedDevice, currentPath: String, devices: List<EnrichedDevice>, target: EnrichedDevice): List<String> {
    if (device.pathsTo.contains(target.name)) {
        return listOf("$currentPath-${target.name}")
    }

    val paths = mutableListOf<String>()
    device.pathsTo.forEach { path ->
        if (!currentPath.contains(path) && devices.first { it.name == path }.canGoToDac == true) {
            paths += walkToDac(devices.first { it.name == path }, "$currentPath-$path", devices, target)
        }
    }
    return paths
}

fun walkToOut(device: EnrichedDevice, currentPath: String, devices: List<EnrichedDevice>, target: String): List<String> {
    if (device.pathsTo.contains(target)) {
        return listOf("$currentPath-${target}")
    }

    val paths = mutableListOf<String>()
    device.pathsTo.forEach { path ->
        if (!currentPath.contains(path) && devices.first { it.name == path }.canGoToOut == true) {
            paths += walkToOut(devices.first { it.name == path }, "$currentPath-$path", devices, target)
        }
    }
    return paths
}
