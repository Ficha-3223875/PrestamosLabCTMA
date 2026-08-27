package com.example.prestamolab_ctma.viewmodel

import androidx.lifecycle.ViewModel
import com.example.prestamolab_ctma.data.repository.InMemoryPrestamoRepository
import com.example.prestamolab_ctma.data.repository.PrestamoRepository
import com.example.prestamolab_ctma.model.Equipo
import com.example.prestamolab_ctma.model.SolicitudPrestamo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class PrestamoUiState(
    val equipos: List<Equipo> = emptyList(),
    val solicitudes: List<SolicitudPrestamo> = emptyList(),
    val mensaje: String? = null
)

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

    fun equipo(id: Int): Equipo? = repository.obtenerEquipo(id)
    fun solicitud(id: Int): SolicitudPrestamo? = repository.obtenerSolicitud(id)

    fun crearSolicitud(equipoId: Int, destino: String, proposito: String, duracion: Int): Boolean {
        val resultado = repository.crearSolicitud(equipoId, destino, proposito, duracion)
        _uiState.value = _uiState.value.copy(
            equipos = repository.obtenerEquipos(),
            solicitudes = repository.obtenerSolicitudes(),
            mensaje = resultado.fold(
                onSuccess = { "Solicitud #${it.id} creada correctamente." },
                onFailure = { it.message ?: "No fue posible crear la solicitud." }
            )
        )
        return resultado.isSuccess
    }

    fun cancelarSolicitud(id: Int): Boolean {
        val resultado = repository.cancelarSolicitud(id)
        _uiState.value = _uiState.value.copy(
            equipos = repository.obtenerEquipos(),
            solicitudes = repository.obtenerSolicitudes(),
            mensaje = resultado.fold(
                onSuccess = { "Solicitud cancelada. El equipo volvió a estar disponible." },
                onFailure = { it.message ?: "No fue posible cancelar la solicitud." }
            )
        )
        return resultado.isSuccess
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(mensaje = null)
    }
}
