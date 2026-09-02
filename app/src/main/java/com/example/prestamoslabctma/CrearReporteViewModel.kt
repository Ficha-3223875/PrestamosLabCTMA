package com.example.prestamoslabctma

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CrearReporteViewModel(
    private val repository: ReporteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearUiState())
    val uiState: StateFlow<CrearUiState> = _uiState.asStateFlow()

    fun actualizarTitulo(valor: String) {
        val tituloLimitado = valor.take(80)

        _uiState.value = _uiState.value.copy(
            titulo = tituloLimitado,
            errorTitulo = if (tituloLimitado.trim().length >= 4) {
                null
            } else {
                _uiState.value.errorTitulo
            }
        )
    }

    fun guardar() {
        val titulo = _uiState.value.titulo.trim()

        if (titulo.isBlank() || titulo.length < 4) {
            _uiState.value = _uiState.value.copy(
                errorTitulo = "El título debe tener al menos 4 caracteres.",
                guardando = false
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                guardando = true,
                errorTitulo = null
            )

            val id = UUID.randomUUID().toString()

            val reporte = Reporte(
                id = id,
                titulo = titulo
            )

            repository.agregar(reporte)

            _uiState.value = _uiState.value.copy(
                guardando = false,
                guardadoId = id
            )
        }
    }
}