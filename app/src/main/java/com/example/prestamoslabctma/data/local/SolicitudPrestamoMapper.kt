package com.example.prestamoslabctma.data.local

import com.example.prestamoslabctma.data.local.entity.SolicitudPrestamoEntity
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo

fun SolicitudPrestamo.toEntity(): SolicitudPrestamoEntity {
    return SolicitudPrestamoEntity(
        id = id,
        equipoId = equipoId,
        ambienteDestino = ambienteDestino,
        proposito = proposito,
        duracionHoras = duracionHoras,
        estado = estado.name
    )
}

fun SolicitudPrestamoEntity.toDomain(): SolicitudPrestamo {
    return SolicitudPrestamo(
        id = id,
        equipoId = equipoId,
        ambienteDestino = ambienteDestino,
        proposito = proposito,
        duracionHoras = duracionHoras,
        estado = EstadoSolicitud.valueOf(estado)
    )
}