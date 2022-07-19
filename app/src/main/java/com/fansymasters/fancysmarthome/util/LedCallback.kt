package com.fansymasters.fancysmarthome.util

import android.bluetooth.BluetoothDevice
import android.util.Log
import no.nordicsemi.android.ble.callback.DataSentCallback
import no.nordicsemi.android.ble.callback.profile.ProfileDataCallback
import no.nordicsemi.android.ble.data.Data

internal open class LedCallback: ProfileDataCallback, DataSentCallback {
    override fun onDataReceived(device: BluetoothDevice, data: Data) {
        Log.e("Piotrek", "onDataReceived - device: $device data: $data")
    }

    override fun onDataSent(device: BluetoothDevice, data: Data) {
        Log.e("Piotrek", "onDataSent - device: $device data: $data")
    }
}