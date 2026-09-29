package com.example.prestamoslabctma.data.local

import com.example.prestamoslabctma.data.local.entity.EquipoEntity
import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo

fun Equipo.toEntity(): EquipoEntity {
    return EquipoEntity(
        id = id,
        nombre = nombre,
        categoria = categoria.name,
        estado = estado.name
    )
}

fun EquipoEntity.toDomain(): Equipo {
    return Equipo(
        id = id,
        nombre = nombre,
        categoria = CategoriaEquipo.valueOf(categoria),
        estado = EstadoEquipo.valueOf(estado)
    )
}