package com.example.prestamoslabctma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prestamoslabctma.data.local.entity.SolicitudPrestamoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudPrestamoDao {

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<SolicitudPrestamoEntity>>

    @Query("SELECT * FROM solicitudes WHERE id = :id")
    suspend fun obtenerPorId(id: Int): SolicitudPrestamoEntity?

    @Insert
    suspend fun insertar(
        solicitud: SolicitudPrestamoEntity
    )

    @Update
    suspend fun actualizar(
        solicitud: SolicitudPrestamoEntity
    )

    @Delete
    suspend fun eliminar(
        solicitud: SolicitudPrestamoEntity
    )

    @Query(
        """
        SELECT EXISTS(
            SELECT 1
            FROM solicitudes
            WHERE equipoId = :equipoId
            AND estado NOT IN (
                'CANCELADA',
                'RECHAZADA',
                'DEVUELTA'
            )
        )
        """
    )
    suspend fun existeSolicitudActiva(
        equipoId: Int
    ): Boolean
}