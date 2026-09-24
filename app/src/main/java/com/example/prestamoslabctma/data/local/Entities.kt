package com.example.prestamoslabctma.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.prestamoslabctma.model.*

@Entity(tableName = "equipos")
data class EquipoEntity(
    @PrimaryKey val id: Int,
    val nombre: String,
    val categoria: String,
    val estado: String
)

@Entity(
    tableName = "solicitudes",
    foreignKeys = [ForeignKey(
        entity = EquipoEntity::class,
        parentColumns = ["id"],
        childColumns = ["equipoId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("equipoId")]
)
data class SolicitudEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val equipoId: Int,
    val ambienteDestino: String,
    val proposito: String,
    val duracionHoras: Int,
    val estado: String,
    val creadaEn: Long,
    val devolucionEn: Long? = null,
    val evidenciaUri: String? = null,
    val evidenciaNombre: String? = null,
    val evidenciaTipo: String? = null,
    val estadoSincronizacion: String = EstadoSincronizacion.LOCAL.name,
    val sensorAcelerometroVerificado: Boolean = false
)

fun EquipoEntity.aDominio() = Equipo(id, nombre, CategoriaEquipo.valueOf(categoria), EstadoEquipo.valueOf(estado))
fun Equipo.aEntity() = EquipoEntity(id, nombre, categoria.name, estado.name)
fun SolicitudEntity.aDominio() = SolicitudPrestamo(
    id, equipoId, ambienteDestino, proposito, duracionHoras, EstadoSolicitud.valueOf(estado),
    creadaEn, devolucionEn, evidenciaUri, evidenciaNombre, evidenciaTipo,
    EstadoSincronizacion.valueOf(estadoSincronizacion), sensorAcelerometroVerificado
)
