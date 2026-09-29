package com.example.prestamoslabctma.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.prestamoslabctma.data.local.dao.EquipoDao
import com.example.prestamoslabctma.data.local.dao.SolicitudPrestamoDao
import com.example.prestamoslabctma.data.local.entity.EquipoEntity
import com.example.prestamoslabctma.data.local.entity.SolicitudPrestamoEntity

@Database(
    entities = [
        EquipoEntity::class,
        SolicitudPrestamoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class PrestamoDatabase : RoomDatabase() {

    abstract fun equipoDao(): EquipoDao

    abstract fun solicitudPrestamoDao(): SolicitudPrestamoDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(db: SupportSQLiteDatabase) {

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS solicitudes (
                        id INTEGER NOT NULL,
                        equipoId INTEGER NOT NULL,
                        ambienteDestino TEXT NOT NULL,
                        proposito TEXT NOT NULL,
                        duracionHoras INTEGER NOT NULL,
                        estado TEXT NOT NULL,
                        PRIMARY KEY(id)
                    )
                    """.trimIndent()
                )
            }
        }
    }
}