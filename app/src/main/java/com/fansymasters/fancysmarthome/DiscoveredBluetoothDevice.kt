package com.fansymasters.fancysmarthome

import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.support.v18.scanner.ScanResult

data class DiscoveredBluetoothDevice(
    val device: BluetoothDevice,
    val result: ScanResult,
    val name: String?,
    val rssi: Int,
    val previousRssi: Int,
    val highestRssi: Int = -128
) {

    companion object {
        fun set(scanResult: ScanResult): DiscoveredBluetoothDevice =
            DiscoveredBluetoothDevice(
                device = scanResult.device,
                result = scanResult,
                name = scanResult.scanRecord?.deviceName,
                rssi = scanResult.rssi,
                previousRssi = scanResult.rssi,
                highestRssi = scanResult.rssi
            )
    }
}

fun List<DiscoveredBluetoothDevice>.deviceDiscovered(scanResult: ScanResult): Boolean =
    find { it.result == scanResult } != null
