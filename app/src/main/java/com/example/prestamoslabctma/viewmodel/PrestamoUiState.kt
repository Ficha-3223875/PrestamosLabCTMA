package com.example.prestamoslabctma.viewmodel

import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.SolicitudPrestamo
import com.example.prestamoslabctma.model.Usuario

data class PrestamoUiState(
    val equipos: List<Equipo> = emptyList(),
    val solicitudes: List<SolicitudPrestamo> = emptyList(),
    val usuario: Usuario = Usuario(
        nombre = "Aprendiz",
        identificacion = "PRL-001",
        rol = "Aprendiz"
    ),
    val mensaje: String? = null,
    val guardando: Boolean = false
)