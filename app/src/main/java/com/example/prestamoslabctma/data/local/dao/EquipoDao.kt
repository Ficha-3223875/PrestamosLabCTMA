package com.example.prestamoslabctma.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prestamoslabctma.data.local.entity.EquipoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipoDao {

    @Query("SELECT * FROM equipos ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<EquipoEntity>>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): EquipoEntity?

    @Insert
    suspend fun insertar(equipo: EquipoEntity)

    @Insert
    suspend fun insertarTodos(equipos: List<EquipoEntity>)

    @Update
    suspend fun actualizar(equipo: EquipoEntity)

    @Delete
    suspend fun eliminar(equipo: EquipoEntity)

    @Query("UPDATE equipos SET estado = :estado WHERE id = :equipoId")
    suspend fun actualizarEstado(equipoId: Int, estado: String)
}