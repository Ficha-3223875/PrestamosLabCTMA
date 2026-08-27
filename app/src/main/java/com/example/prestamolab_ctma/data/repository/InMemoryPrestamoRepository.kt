package com.example.prestamolab_ctma.data.repository

import com.example.prestamolab_ctma.model.CategoriaEquipo
import com.example.prestamolab_ctma.model.Equipo
import com.example.prestamolab_ctma.model.EstadoEquipo
import com.example.prestamolab_ctma.model.EstadoSolicitud
import com.example.prestamolab_ctma.model.SolicitudPrestamo
import com.example.prestamolab_ctma.model.destinoValido
import com.example.prestamolab_ctma.model.duracionValida
import com.example.prestamolab_ctma.model.propositoValido

class InMemoryPrestamoRepository : PrestamoRepository {

    private val equipos = mutableListOf(
        Equipo(1, "Multímetro digital", CategoriaEquipo.ELECTRONICA, EstadoEquipo.DISPONIBLE, "Instrumento para medir voltaje, corriente y resistencia."),
        Equipo(2, "Kit de electrónica", CategoriaEquipo.ELECTRONICA, EstadoEquipo.DISPONIBLE, "Kit para prácticas básicas de circuitos."),
        Equipo(3, "Tableta Android", CategoriaEquipo.TECNOLOGIA, EstadoEquipo.RESERVADO, "Tableta para actividades de formación."),
        Equipo(4, "Cámara digital", CategoriaEquipo.AUDIOVISUAL, EstadoEquipo.DISPONIBLE, "Cámara para registro de actividades."),
        Equipo(5, "Taladro eléctrico", CategoriaEquipo.HERRAMIENTAS, EstadoEquipo.PRESTADO, "Herramienta eléctrica para prácticas.")
    )

    private val solicitudes = mutableListOf<SolicitudPrestamo>()
    private var nextId = 1

    override fun obtenerEquipos(): List<Equipo> = equipos.toList()
    override fun obtenerEquipo(equipoId: Int): Equipo? = equipos.find { it.id == equipoId }
    override fun obtenerSolicitudes(): List<SolicitudPrestamo> = solicitudes.toList()
    override fun obtenerSolicitud(solicitudId: Int): SolicitudPrestamo? =
        solicitudes.find { it.id == solicitudId }

    override fun crearSolicitud(
        equipoId: Int,
        destino: String,
        proposito: String,
        duracionHoras: Int
    ): Result<SolicitudPrestamo> {
        val equipo = obtenerEquipo(equipoId)
            ?: return Result.failure(IllegalArgumentException("El equipo no existe."))

        if (equipo.estado != EstadoEquipo.DISPONIBLE) {
            return Result.failure(IllegalStateException("El equipo no está disponible."))
        }
        if (!destinoValido(destino)) {
            return Result.failure(IllegalArgumentException("El destino es obligatorio."))
        }
        if (!propositoValido(proposito)) {
            return Result.failure(IllegalArgumentException("El propósito debe tener entre 10 y 180 caracteres."))
        }
        if (!duracionValida(duracionHoras)) {
            return Result.failure(IllegalArgumentException("La duración debe estar entre 1 y 8 horas."))
        }

        // Regla RN-06: una doble pulsación no debe crear solicitudes duplicadas.
        val yaSolicitada = solicitudes.any {
            it.equipoId == equipoId && it.estado == EstadoSolicitud.SOLICITADA
        }
        if (yaSolicitada) {
            return Result.failure(IllegalStateException("Ya existe una solicitud activa para este equipo."))
        }

        val solicitud = SolicitudPrestamo(
            id = nextId++,
            equipoId = equipoId,
            ambienteDestino = destino.trim(),
            proposito = proposito.trim(),
            duracionHoras = duracionHoras,
            estado = EstadoSolicitud.SOLICITADA
        )
        solicitudes += solicitud

        val index = equipos.indexOfFirst { it.id == equipoId }
        equipos[index] = equipo.copy(estado = EstadoEquipo.RESERVADO)

        return Result.success(solicitud)
    }

    override fun cancelarSolicitud(solicitudId: Int): Result<Unit> {
        val solicitud = obtenerSolicitud(solicitudId)
            ?: return Result.failure(IllegalArgumentException("La solicitud no existe."))

        if (solicitud.estado != EstadoSolicitud.SOLICITADA) {
            return Result.failure(IllegalStateException("Solo se pueden cancelar solicitudes SOLICITADAS."))
        }

        val index = solicitudes.indexOfFirst { it.id == solicitudId }
        solicitudes[index] = solicitud.copy(estado = EstadoSolicitud.CANCELADA)

        val equipoIndex = equipos.indexOfFirst { it.id == solicitud.equipoId }
        if (equipoIndex >= 0) {
            equipos[equipoIndex] = equipos[equipoIndex].copy(estado = EstadoEquipo.DISPONIBLE)
        }

        return Result.success(Unit)
    }
}
