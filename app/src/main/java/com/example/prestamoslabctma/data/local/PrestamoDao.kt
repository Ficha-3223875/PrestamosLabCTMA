package com.example.prestamoslabctma.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {
    @Query("SELECT * FROM equipos ORDER BY nombre")
    fun observarEquipos(): Flow<List<EquipoEntity>>

    @Query("SELECT * FROM solicitudes ORDER BY creadaEn DESC")
    fun observarSolicitudes(): Flow<List<SolicitudEntity>>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun obtenerEquipo(id: Int): EquipoEntity?

    @Query("SELECT * FROM solicitudes WHERE id = :id")
    suspend fun obtenerSolicitud(id: Int): SolicitudEntity?

    @Query("SELECT COUNT(*) FROM equipos")
    suspend fun contarEquipos(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun guardarEquipos(equipos: List<EquipoEntity>)

    @Insert
    suspend fun insertarSolicitud(solicitud: SolicitudEntity): Long

    @Update
    suspend fun actualizarEquipo(equipo: EquipoEntity)

    @Update
    suspend fun actualizarSolicitud(solicitud: SolicitudEntity)
}
