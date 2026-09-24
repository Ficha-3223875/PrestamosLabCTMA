package com.example.prestamoslabctma.repository

import com.example.prestamoslabctma.model.*
import kotlinx.coroutines.flow.Flow

/** Contrato que mantiene a la UI desacoplada de la fuente de datos. */
interface PrestamoRepository {
    val equipos: Flow<List<Equipo>>
    val solicitudes: Flow<List<SolicitudPrestamo>>
    suspend fun inicializar()
    suspend fun obtenerEquipo(id: Int): Equipo?
    suspend fun obtenerSolicitud(id: Int): SolicitudPrestamo?
    suspend fun crearSolicitud(equipoId: Int, ambiente: String, proposito: String, duracion: Int): Result<SolicitudPrestamo>
    suspend fun cancelarSolicitud(id: Int): Result<Unit>
    suspend fun registrarEntrega(id: Int): Result<Unit>
    suspend fun registrarDevolucion(id: Int, evidencia: EvidenciaDevolucion): Result<Unit>
    suspend fun sincronizar(): Result<Int>
}

data class EvidenciaDevolucion(
    val uri: String,
    val nombre: String,
    val tipoMime: String,
    val acelerometroVerificado: Boolean
)
