package com.example.prestamoslabctma.data.local

import android.content.Context
import androidx.room.Room
import com.example.prestamoslabctma.data.local.database.PrestamoDatabase
import com.example.prestamoslabctma.data.local.entity.EquipoEntity

object DatabaseProvider {

    @Volatile
    private var INSTANCE: PrestamoDatabase? = null

    fun obtenerDatabase(
        context: Context
    ): PrestamoDatabase {

        return INSTANCE ?: synchronized(this) {

            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                PrestamoDatabase::class.java,
                "prestamolab.db"
            )
                .addMigrations(
                    PrestamoDatabase.MIGRATION_1_2
                )
                .build()
                .also {
                    INSTANCE = it
                }
        }
    }
}