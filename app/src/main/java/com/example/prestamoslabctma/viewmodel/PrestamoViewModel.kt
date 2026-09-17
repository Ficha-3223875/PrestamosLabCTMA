package com.example.prestamoslabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.repository.PrestamoRepository
import kotlinx.coroutines.flow.StateFlow

data class ValidacionSolicitud(val ambienteError: String? = null, val propositoError: String? = null, val duracionError: String? = null) { val esValida get() = ambienteError == null && propositoError == null && duracionError == null }

class PrestamoViewModel(private val repository: PrestamoRepository) : ViewModel() {
    val equipos: StateFlow<List<Equipo>> = repository.equipos
    val solicitudes: StateFlow<List<SolicitudPrestamo>> = repository.solicitudes
    fun equipo(id: Int) = repository.obtenerEquipo(id)
    fun solicitud(id: Int) = repository.obtenerSolicitud(id)

    /** Reglas desacopladas de Compose para poder probarlas unitariamente. */
    fun validar(ambiente: String, proposito: String, duracionTexto: String): ValidacionSolicitud {
        val horas = duracionTexto.toIntOrNull()
        return ValidacionSolicitud(
            ambienteError = if (ambiente.isBlank()) "El ambiente o destino es obligatorio" else null,
            propositoError = if (proposito.trim().length !in 10..180) "El propósito debe tener entre 10 y 180 caracteres" else null,
            duracionError = if (horas == null || horas !in 1..8) "La duración debe estar entre 1 y 8 horas" else null
        )
    }
    fun crear(equipoId: Int, ambiente: String, proposito: String, duracionTexto: String): Result<SolicitudPrestamo> {
        val validacion = validar(ambiente, proposito, duracionTexto)
        if (!validacion.esValida) return Result.failure(IllegalArgumentException("Corrige los campos indicados"))
        return repository.crearSolicitud(equipoId, ambiente, proposito, duracionTexto.toInt())
    }
    fun cancelar(id: Int) = repository.cancelarSolicitud(id)
}

class PrestamoViewModelFactory(private val repository: PrestamoRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PrestamoViewModel(repository) as T
}
