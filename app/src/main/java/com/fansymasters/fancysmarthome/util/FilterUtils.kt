package com.fansymasters.fancysmarthome.util

import android.os.ParcelUuid
import no.nordicsemi.android.support.v18.scanner.ScanResult

class FilterUtils {
    private val EDDYSTONE_UUID = ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805f9b34fb")

    private val COMPANY_ID_MICROSOFT = 0x0006
    private val COMPANY_ID_APPLE = 0x004C
    private val COMPANY_ID_NORDIC_SEMI = 0x0059

//    fun isBeacon(result: ScanResult): Boolean {
//        if (result.scanRecord != null) {
//            val record = result.scanRecord
//            val appleData = record!!.getManufacturerSpecificData(COMPANY_ID_APPLE)
//            if (appleData != null) {
//                // iBeacons
//                if (appleData.size == 23 && appleData[0] == 0x02 && appleData[1] == 0x15) return true
//            }
//            val nordicData = record.getManufacturerSpecificData(COMPANY_ID_NORDIC_SEMI)
//            if (nordicData != null) {
//                // Nordic Beacons
//                if (nordicData.size == 23 && nordicData[0] == 0x02 && nordicData[1] == 0x15) return true
//            }
//            val microsoftData = record.getManufacturerSpecificData(COMPANY_ID_MICROSOFT)
//            if (microsoftData != null) {
//                // Microsoft Advertising Beacon
//                if (microsoftData[0] == 0x01) // Scenario Type = Advertising Beacon
//                    return true
//            }
//
//            // Eddystone
//            val eddystoneData = record.getServiceData(EDDYSTONE_UUID)
//            if (eddystoneData != null) return true
//        }
//        return false
//    }
//
//    fun isAirDrop(result: ScanResult): Boolean {
//        if (result.scanRecord != null) {
//            val record = result.scanRecord
//
//            // iPhones and iMacs advertise with AirDrop packets
//            val appleData = record!!.getManufacturerSpecificData(COMPANY_ID_APPLE)
//            return appleData != null && appleData.size > 1 && appleData[0] == 0x10
//        }
//        return false
//    }
}