package com.example.prestamoslabctma.data.local

import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo

fun equiposIniciales(): List<Equipo> {
    return listOf(
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
}