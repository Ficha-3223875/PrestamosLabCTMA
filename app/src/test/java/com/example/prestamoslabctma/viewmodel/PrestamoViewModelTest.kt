package com.example.prestamoslabctma.viewmodel

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.prestamoslabctma.model.EstadoEquipo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PrestamoViewModelTest {

    private fun crearViewModel(): PrestamoViewModel {
        val application =
            ApplicationProvider.getApplicationContext<Application>()

        return PrestamoViewModel(application)
    }

    @Test
    fun estadoInicial_contieneLosEquipos() = runTest {
        val viewModel = crearViewModel()

        assertTrue(
            viewModel.uiState.value.equipos.isNotEmpty()
        )
    }

    @Test
    fun estadoInicial_contieneLosEquiposIniciales() = runTest {
        val viewModel = crearViewModel()

        assertTrue(
            viewModel.uiState.value.equipos.size >= 4
        )
    }

    @Test
    fun obtenerEquipo_inexistente_devuelveNull() = runTest {
        val viewModel = crearViewModel()

        assertNull(
            viewModel.obtenerEquipo(999)
        )
    }

    @Test
    fun estadoInicial_noEstaGuardando() = runTest {
        val viewModel = crearViewModel()

        assertFalse(
            viewModel.uiState.value.guardando
        )
    }

    @Test
    fun estadoInicial_noTieneMensaje() = runTest {
        val viewModel = crearViewModel()

        assertNull(
            viewModel.uiState.value.mensaje
        )
    }

    @Test
    fun estadoInicial_muestraEquipoDisponible() = runTest {
        val viewModel = crearViewModel()

        assertTrue(
            viewModel.uiState.value.equipos.any {
                it.estado == EstadoEquipo.DISPONIBLE
            }
        )
    }
}