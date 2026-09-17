package com.example.prestamoslabctma.repository

import com.example.prestamoslabctma.model.*
import kotlinx.coroutines.flow.StateFlow

/** Contrato que mantiene a la UI desacoplada de la fuente de datos. */
interface PrestamoRepository {
    val equipos: StateFlow<List<Equipo>>
    val solicitudes: StateFlow<List<SolicitudPrestamo>>
    fun obtenerEquipo(id: Int): Equipo?
    fun obtenerSolicitud(id: Int): SolicitudPrestamo?
    fun crearSolicitud(equipoId: Int, ambiente: String, proposito: String, duracion: Int): Result<SolicitudPrestamo>
    fun cancelarSolicitud(id: Int): Result<Unit>
}
