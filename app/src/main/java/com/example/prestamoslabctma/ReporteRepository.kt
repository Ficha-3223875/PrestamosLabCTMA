package com.example.prestamoslabctma

import kotlinx.coroutines.flow.StateFlow

interface ReporteRepository {
    val reportes: StateFlow<List<Reporte>>

    fun agregar(reporte: Reporte)
}