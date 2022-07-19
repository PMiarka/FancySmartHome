package com.fansymasters.fancysmarthome.details

import android.content.Context
import androidx.lifecycle.ViewModel
import com.fansymasters.fancysmarthome.util.BleManager
import com.fansymasters.fancysmarthome.util.DevicesStateReader
import dagger.hilt.android.lifecycle.HiltViewModel
import no.nordicsemi.android.ble.ConnectRequest
import no.nordicsemi.android.log.LogSession
import no.nordicsemi.android.log.Logger
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val devicesStateReader: DevicesStateReader
) : ViewModel() {
    private lateinit var manager: BleManager

    fun connect(context: Context) {
        val discoveredBluetoothDevice =
            devicesStateReader.state.value.first { it.device.address == "66:55:44:33:22:11" }
        val session: LogSession? =
            Logger.newSession(
                context,
                null,
                discoveredBluetoothDevice.device.address,
                discoveredBluetoothDevice.name ?: "name"
            )
        manager = BleManager(context, session)

        var request: ConnectRequest?
        request = manager
            .connect(discoveredBluetoothDevice.device)
            .useAutoConnect(true)
            .then { d -> request = null }
        request?.enqueue()
    }

    fun setLed() {
        manager.turnLed(true)
    }
}