package com.fansymasters.fancysmarthome.util

import com.fansymasters.fancysmarthome.DiscoveredBluetoothDevice
import kotlinx.coroutines.flow.StateFlow
import no.nordicsemi.android.support.v18.scanner.ScanResult

interface DevicesStateReader {
    val state: StateFlow<List<DiscoveredBluetoothDevice>>
    fun setDevices(results: MutableList<ScanResult>)
}
