package com.example.prestamoslabctma.viewmodel

import androidx.lifecycle.ViewModel
import com.example.prestamoslabctma.data.repository.InMemoryPrestamoRepository
import com.example.prestamoslabctma.data.repository.PrestamoRepository
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PrestamoViewModel(
    private val repository: PrestamoRepository = InMemoryPrestamoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        PrestamoUiState(
            equipos = repository.obtenerEquipos(),
            solicitudes = repository.obtenerSolicitudes()
        )
    )

    val uiState: StateFlow<PrestamoUiState> = _uiState.asStateFlow()

    fun obtenerEquipo(id: Int) =
        repository.obtenerEquipo(id)

    fun obtenerSolicitud(id: Int) =
        repository.obtenerSolicitud(id)

    fun crearSolicitud(
        equipoId: Int,
        ambienteDestino: String,
        proposito: String,
        duracionHoras: Int
    ) {

        if (_uiState.value.guardando) {
            return
        }

        _uiState.value = _uiState.value.copy(
            guardando = true,
            mensaje = null
        )

        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = equipoId,
            ambienteDestino = ambienteDestino,
            proposito = proposito,
            duracionHoras = duracionHoras,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        resultado.fold(
            onSuccess = {
                actualizarEstado(
                    mensaje = "Solicitud creada correctamente."
                )
            },
            onFailure = { error ->
                actualizarEstado(
                    mensaje = error.message
                        ?: "No fue posible crear la solicitud."
                )
            }
        )
    }

    fun cancelarSolicitud(id: Int) {

        val resultado = repository.cancelarSolicitud(id)

        resultado.fold(
            onSuccess = {
                actualizarEstado(
                    mensaje = "Solicitud cancelada correctamente."
                )
            },
            onFailure = { error ->
                actualizarEstado(
                    mensaje = error.message
                        ?: "No fue posible cancelar la solicitud."
                )
            }
        )
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(
            mensaje = null
        )
    }

    private fun actualizarEstado(
        mensaje: String
    ) {
        _uiState.value = _uiState.value.copy(
            equipos = repository.obtenerEquipos(),
            solicitudes = repository.obtenerSolicitudes(),
            mensaje = mensaje,
            guardando = false
        )
    }
}