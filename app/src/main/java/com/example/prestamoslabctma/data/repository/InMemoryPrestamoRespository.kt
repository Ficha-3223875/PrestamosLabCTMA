package com.example.prestamoslabctma.data.repository

import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo

class InMemoryPrestamoRepository : PrestamoRepository {

    private val equipos = mutableListOf(
        Equipo(
            id = 1,
            nombre = "Computador portátil Lenovo",
            categoria = CategoriaEquipo.COMPUTO,
            estado = EstadoEquipo.DISPONIBLE
        ),
        Equipo(
            id = 2,
            nombre = "Video Beam Epson",
            categoria = CategoriaEquipo.AUDIOVISUAL,
            estado = EstadoEquipo.DISPONIBLE
        ),
        Equipo(
            id = 3,
            nombre = "Multímetro digital",
            categoria = CategoriaEquipo.LABORATORIO,
            estado = EstadoEquipo.PRESTADO
        ),
        Equipo(
            id = 4,
            nombre = "Taladro eléctrico",
            categoria = CategoriaEquipo.HERRAMIENTA,
            estado = EstadoEquipo.RESERVADO
        )
    )

    private val solicitudes = mutableListOf<SolicitudPrestamo>()

    private var siguienteSolicitudId = 1

    override fun obtenerEquipos(): List<Equipo> {
        return equipos.toList()
    }

    override fun obtenerEquipo(id: Int): Equipo? {
        return equipos.find { it.id == id }
    }

    override fun obtenerSolicitudes(): List<SolicitudPrestamo> {
        return solicitudes.toList()
    }

    override fun obtenerSolicitud(id: Int): SolicitudPrestamo? {
        return solicitudes.find { it.id == id }
    }

    override fun crearSolicitud(
        solicitud: SolicitudPrestamo
    ): Result<Unit> {

        val equipo = obtenerEquipo(solicitud.equipoId)
            ?: return Result.failure(
                IllegalArgumentException("El equipo no existe.")
            )

        if (equipo.estado != EstadoEquipo.DISPONIBLE) {
            return Result.failure(
                IllegalStateException(
                    "El equipo no está disponible para préstamo."
                )
            )
        }

        if (solicitud.ambienteDestino.isBlank()) {
            return Result.failure(
                IllegalArgumentException(
                    "El ambiente de destino es obligatorio."
                )
            )
        }

        if (solicitud.proposito.trim().length !in 10..180) {
            return Result.failure(
                IllegalArgumentException(
                    "El propósito debe tener entre 10 y 180 caracteres."
                )
            )
        }

        if (solicitud.duracionHoras !in 1..8) {
            return Result.failure(
                IllegalArgumentException(
                    "La duración debe estar entre 1 y 8 horas."
                )
            )
        }

        val solicitudActiva = solicitudes.any {
            it.equipoId == solicitud.equipoId &&
                    it.estado != EstadoSolicitud.CANCELADA &&
                    it.estado != EstadoSolicitud.RECHAZADA &&
                    it.estado != EstadoSolicitud.DEVUELTA
        }

        if (solicitudActiva) {
            return Result.failure(
                IllegalStateException(
                    "Ya existe una solicitud activa para este equipo."
                )
            )
        }

        val nuevaSolicitud = solicitud.copy(
            id = siguienteSolicitudId++,
            estado = EstadoSolicitud.SOLICITADA
        )

        solicitudes.add(nuevaSolicitud)

        val indiceEquipo = equipos.indexOfFirst {
            it.id == equipo.id
        }

        if (indiceEquipo != -1) {
            equipos[indiceEquipo] = equipo.copy(
                estado = EstadoEquipo.RESERVADO
            )
        }

        return Result.success(Unit)
    }

    override fun cancelarSolicitud(id: Int): Result<Unit> {

        val indiceSolicitud = solicitudes.indexOfFirst {
            it.id == id
        }

        if (indiceSolicitud == -1) {
            return Result.failure(
                IllegalArgumentException(
                    "La solicitud no existe."
                )
            )
        }

        val solicitud = solicitudes[indiceSolicitud]

        if (solicitud.estado != EstadoSolicitud.SOLICITADA) {
            return Result.failure(
                IllegalStateException(
                    "Solo se pueden cancelar solicitudes en estado SOLICITADA."
                )
            )
        }

        solicitudes[indiceSolicitud] = solicitud.copy(
            estado = EstadoSolicitud.CANCELADA
        )

        val indiceEquipo = equipos.indexOfFirst {
            it.id == solicitud.equipoId
        }

        if (indiceEquipo != -1) {
            equipos[indiceEquipo] = equipos[indiceEquipo].copy(
                estado = EstadoEquipo.DISPONIBLE
            )
        }

        return Result.success(Unit)
    }
}