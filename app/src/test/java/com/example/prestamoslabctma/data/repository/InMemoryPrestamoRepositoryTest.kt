package com.example.prestamoslabctma.data.repository

import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InMemoryPrestamoRepositoryTest {

    private lateinit var repository: InMemoryPrestamoRepository

    @Before
    fun prepararRepositorio() {
        repository = InMemoryPrestamoRepository()
    }

    @Test
    fun obtenerEquipos_debeMostrarEquiposRegistrados() {
        val equipos = repository.obtenerEquipos()

        assertFalse(equipos.isEmpty())
    }

    @Test
    fun obtenerEquipo_conIdExistente_debeRetornarEquipo() {
        val equipo = repository.obtenerEquipo(1)

        assertNotNull(equipo)
        assertEquals(1, equipo?.id)
    }

    @Test
    fun obtenerEquipo_conIdInexistente_debeRetornarNull() {
        val equipo = repository.obtenerEquipo(999)

        assertEquals(null, equipo)
    }

    @Test
    fun crearSolicitud_valida_debeCrearSolicitud() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica de formación",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isSuccess)
        assertEquals(1, repository.obtenerSolicitudes().size)
        assertEquals(
            EstadoSolicitud.SOLICITADA,
            repository.obtenerSolicitudes()[0].estado
        )
    }

    @Test
    fun crearSolicitud_equipoPrestado_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 3,
            ambienteDestino = "Laboratorio",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
        assertEquals(
            EstadoEquipo.PRESTADO,
            repository.obtenerEquipo(3)?.estado
        )
    }

    @Test
    fun crearSolicitud_equipoReservado_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 4,
            ambienteDestino = "Taller",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
        assertEquals(
            EstadoEquipo.RESERVADO,
            repository.obtenerEquipo(4)?.estado
        )
    }

    @Test
    fun crearSolicitud_equipoInexistente_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 999,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_sinAmbiente_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_propositoMenorA10_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Corto",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_propositoMayorA180_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "A".repeat(181),
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_duracionMenorA1_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 0,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_duracionMayorA8_debeRechazar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 9,
            estado = EstadoSolicitud.SOLICITADA
        )

        val resultado = repository.crearSolicitud(solicitud)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun crearSolicitud_dosVecesMismoEquipo_debeEvitarDuplicado() {
        val primeraSolicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        val segundaSolicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de redes",
            proposito = "Realizar otra actividad",
            duracionHoras = 3,
            estado = EstadoSolicitud.SOLICITADA
        )

        val primera = repository.crearSolicitud(primeraSolicitud)
        val segunda = repository.crearSolicitud(segundaSolicitud)

        assertTrue(primera.isSuccess)
        assertTrue(segunda.isFailure)
        assertEquals(1, repository.obtenerSolicitudes().size)
    }

    @Test
    fun crearSolicitud_exitosa_debeReservarEquipo() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        repository.crearSolicitud(solicitud)

        assertEquals(
            EstadoEquipo.RESERVADO,
            repository.obtenerEquipo(1)?.estado
        )
    }

    @Test
    fun cancelarSolicitud_solicitada_debeCancelar() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        repository.crearSolicitud(solicitud)

        val creada = repository.obtenerSolicitudes()[0]

        val resultado = repository.cancelarSolicitud(creada.id)

        assertTrue(resultado.isSuccess)
        assertEquals(
            EstadoSolicitud.CANCELADA,
            repository.obtenerSolicitud(creada.id)?.estado
        )
    }

    @Test
    fun cancelarSolicitud_debeLiberarEquipo() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        repository.crearSolicitud(solicitud)

        val creada = repository.obtenerSolicitudes()[0]

        repository.cancelarSolicitud(creada.id)

        assertEquals(
            EstadoEquipo.DISPONIBLE,
            repository.obtenerEquipo(1)?.estado
        )
    }

    @Test
    fun cancelarSolicitud_inexistente_debeRechazar() {
        val resultado = repository.cancelarSolicitud(999)

        assertTrue(resultado.isFailure)
    }

    @Test
    fun cancelarSolicitud_dosVeces_debeRechazarSegundoIntento() {
        val solicitud = SolicitudPrestamo(
            id = 0,
            equipoId = 1,
            ambienteDestino = "Ambiente de sistemas",
            proposito = "Realizar actividad práctica",
            duracionHoras = 2,
            estado = EstadoSolicitud.SOLICITADA
        )

        repository.crearSolicitud(solicitud)

        val creada = repository.obtenerSolicitudes()[0]

        val primera = repository.cancelarSolicitud(creada.id)
        val segunda = repository.cancelarSolicitud(creada.id)

        assertTrue(primera.isSuccess)
        assertTrue(segunda.isFailure)
    }
}