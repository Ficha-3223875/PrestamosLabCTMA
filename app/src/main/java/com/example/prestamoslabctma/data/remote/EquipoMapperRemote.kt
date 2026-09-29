package com.example.prestamoslabctma.data.remote

import com.example.prestamoslabctma.data.remote.dto.EquipoDto
import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo

fun EquipoDto.toDomain(): Equipo {

    return Equipo(
        id = id,
        nombre = nombre,
        categoria = CategoriaEquipo.valueOf(categoria),
        estado = EstadoEquipo.valueOf(estado)
    )
}