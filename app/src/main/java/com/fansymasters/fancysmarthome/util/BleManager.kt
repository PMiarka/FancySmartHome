package com.fansymasters.fancysmarthome.util

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import com.fansymasters.fancysmarthome.BuildConfig
import no.nordicsemi.android.ble.data.Data
import no.nordicsemi.android.ble.livedata.ObservableBleManager
import no.nordicsemi.android.log.LogContract
import no.nordicsemi.android.log.LogSession
import no.nordicsemi.android.log.Logger
import java.util.*

class BleManager(context: Context, private val session: LogSession?) :
    ObservableBleManager(context) {
    /** Pico BLE servic  */
    private val picoBleService: UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
    private val picoBleLedChar: UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")

    private var ledCharacteristic: BluetoothGattCharacteristic? = null
    private var buttonCharacteristic: BluetoothGattCharacteristic? = null

    override fun log(priority: Int, message: String) {
        if (BuildConfig.DEBUG) {
            Log.println(priority, "BlinkyManager", message)
        }
        Logger.log(session, LogContract.Log.Level.fromPriority(priority), message)
    }

    override fun getGattCallback(): BleManagerGattCallback = object : BleManagerGattCallback() {

        override fun initialize() {
            Log.e("Piotrek", "Initialize")
            setNotificationCallback(buttonCharacteristic)
//                .with(buttonCallback)
            setNotificationCallback(ledCharacteristic)
//                .with(ledCallback)
            readCharacteristic(buttonCharacteristic).enqueue()
//                .with(buttonCallback).enqueue()
            enableNotifications(ledCharacteristic).enqueue()
        }

        override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
            val service = gatt.getService(picoBleService)
            if (service != null) {
                ledCharacteristic = service.getCharacteristic(picoBleLedChar)
            }
            var writeRequest = false
            if (ledCharacteristic != null) {
                val ledProperties = ledCharacteristic!!.properties
                writeRequest = ledProperties and BluetoothGattCharacteristic.PROPERTY_WRITE > 0
            }
            return service != null
        }

        override fun onServicesInvalidated() {
            buttonCharacteristic = null
            ledCharacteristic = null
        }

    }

    private val ledCallback = object : LedCallback(){}

    fun turnLed(on: Boolean) {
        log(Log.VERBOSE, "Turning LED " + (if (on) "ON" else "OFF") + "...")
        writeCharacteristic(
            ledCharacteristic,
            Data.from("999"),
            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
        )
            .with(ledCallback).enqueue()
    }
}