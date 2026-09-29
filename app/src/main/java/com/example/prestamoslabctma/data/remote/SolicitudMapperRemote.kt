package com.example.prestamoslabctma.data.remote

import com.example.prestamoslabctma.data.remote.dto.SolicitudDto
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo

fun SolicitudDto.toDomain(): SolicitudPrestamo {
    return SolicitudPrestamo(
        id = id,
        equipoId = equipoId,
        ambienteDestino = ambienteDestino,
        proposito = proposito,
        duracionHoras = duracionHoras,
        estado = EstadoSolicitud.valueOf(estado)
    )
}