package com.example.prestamoslabctma

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CrearReporteRoute(
    viewModel: CrearReporteViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CrearReporteContent(
        uiState = uiState,
        onTituloChange = viewModel::actualizarTitulo,
        onGuardar = viewModel::guardar
    )
}