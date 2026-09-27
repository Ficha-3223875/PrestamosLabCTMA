package com.prestamolab.ctma

import android.app.Application
import com.prestamolab.ctma.data.EquipmentRepository
import com.prestamolab.ctma.data.RoomEquipmentRepository
import com.prestamolab.ctma.data.local.PrestamoDatabase
import com.prestamolab.ctma.data.preferences.UserPreferences
import com.prestamolab.ctma.data.remote.FakeRemoteDataSource

class PrestamoLabApplication : Application() {
    lateinit var repository: EquipmentRepository
        private set

    override fun onCreate() {
        super.onCreate()
        repository = RoomEquipmentRepository(
            db = PrestamoDatabase.getInstance(this),
            preferences = UserPreferences(this),
            // La guía permite API/mock. Se usa fake por defecto para que el prototipo funcione sin Internet.
            remote = FakeRemoteDataSource()
        )
    }
}
