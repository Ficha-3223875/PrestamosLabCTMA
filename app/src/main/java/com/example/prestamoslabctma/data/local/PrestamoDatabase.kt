package com.example.prestamoslabctma.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EquipoEntity::class, SolicitudEntity::class], version = 1, exportSchema = true)
abstract class PrestamoDatabase : RoomDatabase() {
    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile private var instancia: PrestamoDatabase? = null
        fun obtener(context: Context): PrestamoDatabase = instancia ?: synchronized(this) {
            instancia ?: Room.databaseBuilder(
                context.applicationContext,
                PrestamoDatabase::class.java,
                "prestamolab.db"
            ).build().also { instancia = it }
        }
    }
}
