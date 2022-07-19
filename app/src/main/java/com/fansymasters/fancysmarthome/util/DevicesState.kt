package com.fansymasters.fancysmarthome.util

import com.fansymasters.fancysmarthome.DiscoveredBluetoothDevice
import kotlinx.coroutines.flow.MutableStateFlow
import no.nordicsemi.android.support.v18.scanner.ScanResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DevicesState @Inject constructor() : DevicesStateReader {
    override val state = MutableStateFlow<List<DiscoveredBluetoothDevice>>(listOf())

    override fun setDevices(results: MutableList<ScanResult>) {
        state.value =
            results.sortedBy { it.device.address }.map { DiscoveredBluetoothDevice.set(it) }
    }
}