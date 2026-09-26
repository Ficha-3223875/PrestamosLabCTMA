package com.example.prestamoslabctma.viewmodel

import com.example.prestamoslabctma.data.repository.InMemoryPrestamoRepository
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.model.EstadoSolicitud
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PrestamoViewModelTest {

    private lateinit var viewModel: PrestamoViewModel

    @Before
    fun prepararViewModel() {
        viewModel = PrestamoViewModel(
            InMemoryPrestamoRepository()
        )
    }

    @Test
    fun estadoInicial_contieneLosEquipos() {
        val estado = viewModel.uiState.value

        assertFalse(estado.equipos.isEmpty())
        assertEquals(4, estado.equipos.size)
    }

    @Test
    fun estadoInicial_noContieneSolicitudes() {
        val estado = viewModel.uiState.value

        assertTrue(estado.solicitudes.isEmpty())
        assertFalse(estado.guardando)
        assertNull(estado.mensaje)
    }

    @Test
    fun obtenerEquipo_existente_devuelveEquipo() {
        val equipo = viewModel.obtenerEquipo(1)

        assertNotNull(equipo)
        assertEquals(1, equipo?.id)
        assertEquals(
            "Computador portátil Lenovo",
            equipo?.nombre
        )
    }

    @Test
    fun obtenerEquipo_inexistente_devuelveNull() {
        val equipo = viewModel.obtenerEquipo(999)

        assertNull(equipo)
    }

    @Test
    fun crearSolicitud_valida_actualizaElEstado() {
        viewModel.crearSolicitud(
            equipoId = 1,
            ambienteDestino = "Laboratorio 1",
            proposito = "Realizar actividad práctica de formación",
            duracionHoras = 4
        )

        val estado = viewModel.uiState.value

        assertEquals(1, estado.solicitudes.size)
        assertEquals(
            "Solicitud creada correctamente.",
            estado.mensaje
        )
        assertFalse(estado.guardando)
        assertEquals(
            EstadoSolicitud.SOLICITADA,
            estado.solicitudes.first().estado
        )
    }

    @Test
    fun crearSolicitud_equipoNoDisponible_muestraError() {
        viewModel.crearSolicitud(
            equipoId = 3,
            ambienteDestino = "Laboratorio 1",
            proposito = "Realizar actividad práctica de formación",
            duracionHoras = 2
        )

        val estado = viewModel.uiState.value

        assertTrue(estado.solicitudes.isEmpty())
        assertEquals(
            "El equipo no está disponible para préstamo.",
            estado.mensaje
        )
        assertFalse(estado.guardando)
    }

    @Test
    fun cancelarSolicitud_existente_actualizaEstado() {
        viewModel.crearSolicitud(
            equipoId = 1,
            ambienteDestino = "Laboratorio 1",
            proposito = "Realizar actividad práctica de formación",
            duracionHoras = 3
        )

        val solicitudId = viewModel.uiState.value
            .solicitudes
            .first()
            .id

        viewModel.cancelarSolicitud(solicitudId)

        val estado = viewModel.uiState.value
        val solicitud = estado.solicitudes.first()
        val equipo = estado.equipos.first { it.id == 1 }

        assertEquals(
            EstadoSolicitud.CANCELADA,
            solicitud.estado
        )
        assertEquals(
            "Solicitud cancelada correctamente.",
            estado.mensaje
        )
        assertEquals(
            EstadoEquipo.DISPONIBLE,
            equipo.estado
        )
    }

    @Test
    fun limpiarMensaje_eliminaElMensajeActual() {
        viewModel.crearSolicitud(
            equipoId = 1,
            ambienteDestino = "Laboratorio 1",
            proposito = "Realizar actividad práctica de formación",
            duracionHoras = 2
        )

        assertNotNull(viewModel.uiState.value.mensaje)

        viewModel.limpiarMensaje()

        assertNull(viewModel.uiState.value.mensaje)
    }
}