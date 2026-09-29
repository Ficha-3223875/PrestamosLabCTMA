package com.example.prestamoslabctma.data.repository

import com.example.prestamoslabctma.data.local.dao.EquipoDao
import com.example.prestamoslabctma.data.local.dao.SolicitudPrestamoDao
import com.example.prestamoslabctma.data.local.equiposIniciales
import com.example.prestamoslabctma.data.local.toDomain
import com.example.prestamoslabctma.data.local.toEntity
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class RoomPrestamoRepository(
    private val equipoDao: EquipoDao,
    private val solicitudDao: SolicitudPrestamoDao
) {

    fun obtenerEquipos(): Flow<List<Equipo>> {
        return equipoDao
            .obtenerTodos()
            .map { equipos ->
                equipos.map { it.toDomain() }
            }
    }

    suspend fun obtenerEquipo(
        id: Int
    ): Equipo? {
        return equipoDao
            .obtenerPorId(id)
            ?.toDomain()
    }

    fun obtenerSolicitudes(): Flow<List<SolicitudPrestamo>> {
        return solicitudDao
            .obtenerTodas()
            .map { solicitudes ->
                solicitudes.map { it.toDomain() }
            }
    }

    suspend fun obtenerSolicitud(
        id: Int
    ): SolicitudPrestamo? {
        return solicitudDao
            .obtenerPorId(id)
            ?.toDomain()
    }

    suspend fun crearSolicitud(
        solicitud: SolicitudPrestamo
    ): Boolean {

        val equipo =
            equipoDao.obtenerPorId(
                solicitud.equipoId
            ) ?: return false

        if (
            equipo.estado !=
            EstadoEquipo.DISPONIBLE.name
        ) {
            return false
        }

        val ambiente =
            solicitud.ambienteDestino.trim()

        if (
            ambiente.length !in 3..100
        ) {
            throw IllegalArgumentException(
                "El ambiente de destino debe tener entre 3 y 100 caracteres."
            )
        }

        val proposito =
            solicitud.proposito.trim()

        if (
            proposito.length !in 10..180
        ) {
            throw IllegalArgumentException(
                "El propósito debe tener entre 10 y 180 caracteres."
            )
        }

        if (
            solicitud.duracionHoras !in 1..8
        ) {
            throw IllegalArgumentException(
                "La duración debe estar entre 1 y 8 horas."
            )
        }

        if (
            solicitudDao.existeSolicitudActiva(
                solicitud.equipoId
            )
        ) {
            return false
        }

        val solicitudLimpia =
            solicitud.copy(
                ambienteDestino = ambiente,
                proposito = proposito
            )

        solicitudDao.insertar(
            solicitudLimpia.toEntity()
        )

        equipoDao.actualizarEstado(
            equipoId = solicitud.equipoId,
            estado = EstadoEquipo.RESERVADO.name
        )

        return true
    }

    suspend fun actualizarSolicitud(
        solicitud: SolicitudPrestamo
    ) {
        solicitudDao.actualizar(
            solicitud.toEntity()
        )
    }

    suspend fun cancelarSolicitud(
        id: Int
    ): Boolean {

        val solicitud =
            solicitudDao.obtenerPorId(id)
                ?: return false

        if (
            solicitud.estado !=
            EstadoSolicitud.SOLICITADA.name
        ) {
            return false
        }

        solicitudDao.actualizar(
            solicitud.copy(
                estado =
                    EstadoSolicitud.CANCELADA.name
            )
        )

        equipoDao.actualizarEstado(
            equipoId = solicitud.equipoId,
            estado = EstadoEquipo.DISPONIBLE.name
        )

        return true
    }

    suspend fun devolverPrestamo(
        id: Int
    ): Boolean {

        val solicitud =
            solicitudDao.obtenerPorId(id)
                ?: return false

        if (
            solicitud.estado !=
            EstadoSolicitud.APROBADA.name &&
            solicitud.estado !=
            EstadoSolicitud.ENTREGADA.name
        ) {
            return false
        }

        solicitudDao.actualizar(
            solicitud.copy(
                estado =
                    EstadoSolicitud.DEVUELTA.name
            )
        )

        equipoDao.actualizarEstado(
            equipoId = solicitud.equipoId,
            estado = EstadoEquipo.DISPONIBLE.name
        )

        return true
    }

    suspend fun eliminarSolicitud(
        solicitud: SolicitudPrestamo
    ) {
        solicitudDao.eliminar(
            solicitud.toEntity()
        )
    }

    suspend fun cargarDatosIniciales() {

        val equiposActuales =
            equipoDao.obtenerTodos().first()

        if (equiposActuales.isEmpty()) {

            val equipos =
                equiposIniciales()

            equipoDao.insertarTodos(
                equipos.map {
                    it.toEntity()
                }
            )
        }
    }
}