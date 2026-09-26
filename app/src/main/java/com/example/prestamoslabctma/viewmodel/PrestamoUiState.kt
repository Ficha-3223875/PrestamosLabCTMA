package com.example.prestamoslabctma.viewmodel

import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.SolicitudPrestamo

data class PrestamoUiState(
    val equipos: List<Equipo> = emptyList(),
    val solicitudes: List<SolicitudPrestamo> = emptyList(),
    val mensaje: String? = null,
    val guardando: Boolean = false
)