package com.example.prestamoslabctma

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MemoriaReporteRepository : ReporteRepository {

    private val _reportes = MutableStateFlow<List<Reporte>>(emptyList())

    override val reportes: StateFlow<List<Reporte>> =
        _reportes.asStateFlow()

    override fun agregar(reporte: Reporte) {
        _reportes.value = _reportes.value + reporte
    }
}