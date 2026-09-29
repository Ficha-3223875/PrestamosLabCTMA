package com.example.prestamoslabctma.data.repository

import com.example.prestamoslabctma.data.remote.api.PrestamoApi
import com.example.prestamoslabctma.data.remote.dto.CrearSolicitudDto
import com.example.prestamoslabctma.data.remote.dto.DevolucionDto
import com.example.prestamoslabctma.data.remote.dto.EquipoDto
import com.example.prestamoslabctma.data.remote.dto.SolicitudDto
import com.example.prestamoslabctma.data.remote.toDomain
import com.example.prestamoslabctma.data.remote.toDomain as solicitudToDomain
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.SolicitudPrestamo

class RemotePrestamoRepository(
    private val api: PrestamoApi
) {

    suspend fun obtenerEquipos(): List<Equipo> {
        return api
            .obtenerEquipos()
            .map { it.toDomain() }
    }

    suspend fun obtenerSolicitudes(): List<SolicitudPrestamo> {
        return api
            .obtenerSolicitudes()
            .map { it.solicitudToDomain() }
    }

    suspend fun obtenerSolicitud(
        id: Int
    ): SolicitudPrestamo {
        return api
            .obtenerSolicitud(id)
            .solicitudToDomain()
    }

    suspend fun crearSolicitud(
        solicitud: SolicitudPrestamo
    ): SolicitudPrestamo {

        val dto =
            CrearSolicitudDto(
                equipoId = solicitud.equipoId,
                ambienteDestino =
                    solicitud.ambienteDestino,
                proposito =
                    solicitud.proposito,
                duracionHoras =
                    solicitud.duracionHoras
            )

        return api
            .crearSolicitud(dto)
            .solicitudToDomain()
    }

    suspend fun devolverPrestamo(
        id: Int,
        observacion: String = ""
    ): SolicitudPrestamo {

        val dto =
            DevolucionDto(
                observacion = observacion
            )

        return api
            .devolverPrestamo(
                id = id,
                devolucion = dto
            )
            .solicitudToDomain()
    }
}