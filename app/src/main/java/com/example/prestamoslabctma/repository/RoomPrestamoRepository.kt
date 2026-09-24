package com.example.prestamoslabctma.repository

import androidx.room.withTransaction
import com.example.prestamoslabctma.data.local.*
import com.example.prestamoslabctma.data.remote.*
import com.example.prestamoslabctma.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class RoomPrestamoRepository(
    private val db: PrestamoDatabase,
    private val api: PrestamoApi?
) : PrestamoRepository {
    private val dao = db.prestamoDao()
    override val equipos: Flow<List<Equipo>> = dao.observarEquipos().map { lista -> lista.map(EquipoEntity::aDominio) }
    override val solicitudes: Flow<List<SolicitudPrestamo>> = dao.observarSolicitudes().map { lista -> lista.map(SolicitudEntity::aDominio) }

    override suspend fun inicializar() {
        if (dao.contarEquipos() == 0) dao.guardarEquipos(InMemoryPrestamoRepository.datosDemo().map(Equipo::aEntity))
    }

    override suspend fun obtenerEquipo(id: Int) = dao.obtenerEquipo(id)?.aDominio()
    override suspend fun obtenerSolicitud(id: Int) = dao.obtenerSolicitud(id)?.aDominio()

    override suspend fun crearSolicitud(equipoId: Int, ambiente: String, proposito: String, duracion: Int): Result<SolicitudPrestamo> = runCatching {
        require(ambiente.isNotBlank()) { "El ambiente o destino es obligatorio" }
        require(proposito.trim().length in 10..180) { "El propósito debe tener entre 10 y 180 caracteres" }
        require(duracion in 1..8) { "La duración debe estar entre 1 y 8 horas" }
        db.withTransaction {
            val equipo = dao.obtenerEquipo(equipoId) ?: error("Equipo inexistente")
            check(equipo.estado == EstadoEquipo.DISPONIBLE.name) { "El equipo no está disponible" }
            val entity = SolicitudEntity(equipoId = equipoId, ambienteDestino = ambiente.trim(), proposito = proposito.trim(), duracionHoras = duracion, estado = EstadoSolicitud.SOLICITADA.name, creadaEn = System.currentTimeMillis())
            val id = dao.insertarSolicitud(entity).toInt()
            dao.actualizarEquipo(equipo.copy(estado = EstadoEquipo.RESERVADO.name))
            entity.copy(id = id).aDominio()
        }
    }

    override suspend fun cancelarSolicitud(id: Int): Result<Unit> = cambiarEstado(id, setOf(EstadoSolicitud.SOLICITADA), EstadoSolicitud.CANCELADA, EstadoEquipo.DISPONIBLE)

    override suspend fun registrarEntrega(id: Int): Result<Unit> = cambiarEstado(id, setOf(EstadoSolicitud.SOLICITADA, EstadoSolicitud.APROBADA), EstadoSolicitud.ENTREGADA, EstadoEquipo.PRESTADO)

    private suspend fun cambiarEstado(id: Int, permitidos: Set<EstadoSolicitud>, nuevo: EstadoSolicitud, equipoNuevo: EstadoEquipo): Result<Unit> = runCatching {
        db.withTransaction {
            val solicitud = dao.obtenerSolicitud(id) ?: error("Solicitud inexistente")
            check(EstadoSolicitud.valueOf(solicitud.estado) in permitidos) { "Operación no permitida para el estado ${solicitud.estado}" }
            val equipo = dao.obtenerEquipo(solicitud.equipoId) ?: error("Equipo inexistente")
            dao.actualizarSolicitud(solicitud.copy(estado = nuevo.name, estadoSincronizacion = EstadoSincronizacion.LOCAL.name))
            dao.actualizarEquipo(equipo.copy(estado = equipoNuevo.name))
        }
    }

    override suspend fun registrarDevolucion(id: Int, evidencia: EvidenciaDevolucion): Result<Unit> = runCatching {
        require(evidencia.uri.isNotBlank()) { "Selecciona una evidencia fotográfica" }
        db.withTransaction {
            val solicitud = dao.obtenerSolicitud(id) ?: error("Solicitud inexistente")
            check(solicitud.estado == EstadoSolicitud.ENTREGADA.name) { "Solo se devuelve un préstamo ENTREGADO" }
            val equipo = dao.obtenerEquipo(solicitud.equipoId) ?: error("Equipo inexistente")
            dao.actualizarSolicitud(solicitud.copy(
                estado = EstadoSolicitud.DEVUELTA.name,
                devolucionEn = System.currentTimeMillis(),
                evidenciaUri = evidencia.uri,
                evidenciaNombre = evidencia.nombre,
                evidenciaTipo = evidencia.tipoMime,
                estadoSincronizacion = EstadoSincronizacion.LOCAL.name,
                sensorAcelerometroVerificado = evidencia.acelerometroVerificado
            ))
            dao.actualizarEquipo(equipo.copy(estado = EstadoEquipo.DISPONIBLE.name))
        }
    }

    override suspend fun sincronizar(): Result<Int> = runCatching {
        val servicio = api ?: error("Servicio remoto no configurado para este ambiente")
        val catalogoRemoto = servicio.equipos()
        if (catalogoRemoto.isNotEmpty()) {
            dao.guardarEquipos(catalogoRemoto.map { dto ->
                EquipoEntity(dto.id, dto.nombre, dto.categoria, dto.estado)
            })
        }
        val locales = dao.observarSolicitudes().first()
        var sincronizadas = 0
        for (local in locales.filter { it.estadoSincronizacion != EstadoSincronizacion.SINCRONIZADA.name }) {
            dao.actualizarSolicitud(local.copy(estadoSincronizacion = EstadoSincronizacion.SUBIENDO.name))
            try {
                if (local.estado == EstadoSolicitud.DEVUELTA.name && local.evidenciaUri != null) {
                    servicio.enviarDevolucion(DevolucionDto(local.id, local.evidenciaUri, local.devolucionEn ?: System.currentTimeMillis()))
                } else {
                    servicio.enviarSolicitud(SolicitudDto(local.id, local.equipoId, local.estado, local.ambienteDestino, local.proposito, local.duracionHoras))
                }
                dao.actualizarSolicitud(local.copy(estadoSincronizacion = EstadoSincronizacion.SINCRONIZADA.name))
                sincronizadas++
            } catch (e: Exception) {
                dao.actualizarSolicitud(local.copy(estadoSincronizacion = EstadoSincronizacion.FALLIDA.name))
                throw e
            }
        }
        sincronizadas
    }
}
