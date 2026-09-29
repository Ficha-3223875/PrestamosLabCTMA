package com.example.prestamoslabctma.data.remote.dto

data class CrearSolicitudDto(
    val equipoId: Int,
    val ambienteDestino: String,
    val proposito: String,
    val duracionHoras: Int
)