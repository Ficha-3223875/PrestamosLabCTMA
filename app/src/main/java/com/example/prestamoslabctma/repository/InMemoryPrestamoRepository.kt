package com.example.prestamoslabctma.repository

import com.example.prestamoslabctma.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/** Fuente simulada compartida durante la ejecución. No pretende persistir al cerrar la app. */
class InMemoryPrestamoRepository(
    equiposIniciales: List<Equipo> = datosDemo()
) : PrestamoRepository {
    private val _equipos = MutableStateFlow(equiposIniciales)
    override val equipos: StateFlow<List<Equipo>> = _equipos.asStateFlow()
    private val _solicitudes = MutableStateFlow<List<SolicitudPrestamo>>(emptyList())
    override val solicitudes: StateFlow<List<SolicitudPrestamo>> = _solicitudes.asStateFlow()
    private var siguienteId = 1

    override fun obtenerEquipo(id: Int) = _equipos.value.find { it.id == id }
    override fun obtenerSolicitud(id: Int) = _solicitudes.value.find { it.id == id }

    @Synchronized
    override fun crearSolicitud(equipoId: Int, ambiente: String, proposito: String, duracion: Int): Result<SolicitudPrestamo> {
        val equipo = obtenerEquipo(equipoId) ?: return Result.failure(IllegalArgumentException("Equipo inexistente"))
        if (equipo.estado != EstadoEquipo.DISPONIBLE) return Result.failure(IllegalStateException("El equipo no está disponible"))
        if (ambiente.isBlank()) return Result.failure(IllegalArgumentException("El ambiente o destino es obligatorio"))
        if (proposito.trim().length !in 10..180) return Result.failure(IllegalArgumentException("El propósito debe tener entre 10 y 180 caracteres"))
        if (duracion !in 1..8) return Result.failure(IllegalArgumentException("La duración debe estar entre 1 y 8 horas"))

        // La operación completa es sincronizada: evita reservas duplicadas por doble pulsación.
        val solicitud = SolicitudPrestamo(siguienteId++, equipoId, ambiente.trim(), proposito.trim(), duracion, EstadoSolicitud.SOLICITADA)
        _solicitudes.value = _solicitudes.value + solicitud
        _equipos.value = _equipos.value.map { if (it.id == equipoId) it.copy(estado = EstadoEquipo.RESERVADO) else it }
        return Result.success(solicitud)
    }

    @Synchronized
    override fun cancelarSolicitud(id: Int): Result<Unit> {
        val solicitud = obtenerSolicitud(id) ?: return Result.failure(IllegalArgumentException("Solicitud inexistente"))
        if (solicitud.estado != EstadoSolicitud.SOLICITADA) return Result.failure(IllegalStateException("Solo se puede cancelar una solicitud SOLICITADA"))
        _solicitudes.value = _solicitudes.value.map { if (it.id == id) it.copy(estado = EstadoSolicitud.CANCELADA) else it }
        _equipos.value = _equipos.value.map { if (it.id == solicitud.equipoId) it.copy(estado = EstadoEquipo.DISPONIBLE) else it }
        return Result.success(Unit)
    }

    companion object {
        fun datosDemo() = listOf(
            Equipo(1, "Multímetro digital", CategoriaEquipo.MEDICION, EstadoEquipo.DISPONIBLE),
            Equipo(2, "Kit Arduino Uno", CategoriaEquipo.ELECTRONICA, EstadoEquipo.DISPONIBLE),
            Equipo(3, "Portátil de laboratorio", CategoriaEquipo.COMPUTO, EstadoEquipo.DISPONIBLE),
            Equipo(4, "Cámara digital", CategoriaEquipo.AUDIOVISUAL, EstadoEquipo.DISPONIBLE),
            Equipo(5, "Taladro inalámbrico", CategoriaEquipo.HERRAMIENTA, EstadoEquipo.PRESTADO),
            Equipo(6, "Osciloscopio", CategoriaEquipo.MEDICION, EstadoEquipo.DISPONIBLE)
        )
    }
}
