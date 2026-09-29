package com.example.prestamoslabctma.data.repository

import android.content.Context
import com.example.prestamoslabctma.data.local.DatabaseProvider

object RepositoryProvider {

    fun obtenerRepository(
        context: Context
    ): RoomPrestamoRepository {

        val database = DatabaseProvider.obtenerDatabase(context)

        return RoomPrestamoRepository(
            equipoDao = database.equipoDao(),
            solicitudDao = database.solicitudPrestamoDao()
        )
    }
}