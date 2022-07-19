package com.fansymasters.fancysmarthome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fansymasters.fancysmarthome.util.DevicesStateReader
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import no.nordicsemi.android.support.v18.scanner.ScanResult
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val devicesStateReader: DevicesStateReader
) : ViewModel() {

    val state: StateFlow<List<DiscoveredBluetoothDevice>> =
        devicesStateReader.state.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    fun onDevicesFound(results: MutableList<ScanResult>) {
        devicesStateReader.setDevices(results)
    }
}