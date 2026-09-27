package com.example.prestamolab_ctma.data.remote
import com.example.prestamolab_ctma.model.*
fun EquipoDto.toDomain()=Equipo(id,nombre,runCatching{CategoriaEquipo.valueOf(categoria)}.getOrDefault(CategoriaEquipo.ELECTRONICA),runCatching{EstadoEquipo.valueOf(estado)}.getOrDefault(EstadoEquipo.DISPONIBLE),descripcion)
fun SolicitudDto.toDomain()=SolicitudPrestamo(id,equipoId,ambienteDestino,proposito,duracionHoras,runCatching{EstadoSolicitud.valueOf(estado)}.getOrDefault(EstadoSolicitud.SOLICITADA))
