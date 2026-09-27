package com.prestamolab.ctma.util

import android.content.Context
import android.os.BatteryManager
import android.os.StatFs

data class DeviceStatus(val batteryPercent: Int, val freeStorageGb: Double)

fun readDeviceStatus(context: Context): DeviceStatus {
    val battery = context.getSystemService(BatteryManager::class.java)
        ?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: -1
    val stat = StatFs(context.filesDir.absolutePath)
    val freeGb = stat.availableBytes / 1024.0 / 1024.0 / 1024.0
    return DeviceStatus(battery, freeGb)
}
