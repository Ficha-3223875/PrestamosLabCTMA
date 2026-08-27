package com.example.prestamolab_ctma

import com.example.prestamolab_ctma.data.repository.InMemoryPrestamoRepository
import com.example.prestamolab_ctma.model.EstadoEquipo
import com.example.prestamolab_ctma.model.EstadoSolicitud
import com.example.prestamolab_ctma.model.destinoValido
import com.example.prestamolab_ctma.model.duracionValida
import com.example.prestamolab_ctma.model.propositoValido
import org.junit.Assert.*
import org.junit.Test

class PrestamoRepositoryTest {
    @Test fun destinoVacioEsInvalido() {
        assertFalse(destinoValido(""))
        assertFalse(destinoValido("   "))
        assertTrue(destinoValido("Laboratorio 1"))
    }

    @Test fun limitesProposito() {
        assertFalse(propositoValido("123456789"))
        assertTrue(propositoValido("1234567890"))
        assertTrue(propositoValido("a".repeat(180)))
        assertFalse(propositoValido("a".repeat(181)))
    }

    @Test fun limitesDuracion() {
        assertFalse(duracionValida(0))
        assertTrue(duracionValida(1))
        assertTrue(duracionValida(8))
        assertFalse(duracionValida(9))
    }

    @Test fun solicitudValidaReservaEquipo() {
        val repo = InMemoryPrestamoRepository()
        val result = repo.crearSolicitud(1, "Laboratorio 1", "Realizar práctica de electrónica", 4)
        assertTrue(result.isSuccess)
        assertEquals(EstadoSolicitud.SOLICITADA, result.getOrThrow().estado)
        assertEquals(EstadoEquipo.RESERVADO, repo.obtenerEquipo(1)?.estado)
    }

    @Test fun equipoNoDisponibleEsRechazado() {
        val repo = InMemoryPrestamoRepository()
        assertTrue(repo.crearSolicitud(3, "Laboratorio 1", "Realizar práctica de electrónica", 2).isFailure)
        assertTrue(repo.crearSolicitud(5, "Laboratorio 1", "Realizar práctica de electrónica", 2).isFailure)
        assertEquals(0, repo.obtenerSolicitudes().size)
    }

    @Test fun dobleSolicitudMismoEquipoEsRechazada() {
        val repo = InMemoryPrestamoRepository()
        assertTrue(repo.crearSolicitud(1, "Lab 1", "Realizar una práctica de electrónica", 2).isSuccess)
        assertTrue(repo.crearSolicitud(1, "Lab 1", "Realizar otra práctica de electrónica", 2).isFailure)
        assertEquals(1, repo.obtenerSolicitudes().size)
    }

    @Test fun idsInexistentesSonRecuperables() {
        val repo = InMemoryPrestamoRepository()
        assertNull(repo.obtenerEquipo(999))
        assertNull(repo.obtenerSolicitud(999))
        assertTrue(repo.cancelarSolicitud(999).isFailure)
        assertTrue(repo.crearSolicitud(999, "Lab 1", "Realizar una práctica de electrónica", 2).isFailure)
    }

    @Test fun cancelarLiberaEquipo() {
        val repo = InMemoryPrestamoRepository()
        val solicitud = repo.crearSolicitud(1, "Lab 1", "Realizar una práctica de electrónica", 2).getOrThrow()
        assertTrue(repo.cancelarSolicitud(solicitud.id).isSuccess)
        assertEquals(EstadoSolicitud.CANCELADA, repo.obtenerSolicitud(solicitud.id)?.estado)
        assertEquals(EstadoEquipo.DISPONIBLE, repo.obtenerEquipo(1)?.estado)
    }

    @Test fun solicitudCanceladaNoPuedeCancelarseDosVeces() {
        val repo = InMemoryPrestamoRepository()
        val solicitud = repo.crearSolicitud(1, "Lab 1", "Realizar una práctica de electrónica", 2).getOrThrow()
        assertTrue(repo.cancelarSolicitud(solicitud.id).isSuccess)
        assertTrue(repo.cancelarSolicitud(solicitud.id).isFailure)
        assertEquals(EstadoSolicitud.CANCELADA, repo.obtenerSolicitud(solicitud.id)?.estado)
    }

    @Test fun datosSeMantienenMientrasViveElRepositorio() {
        val repo = InMemoryPrestamoRepository()
        val solicitud = repo.crearSolicitud(2, "Laboratorio 2", "Realizar práctica de circuitos", 3).getOrThrow()
        assertEquals(solicitud, repo.obtenerSolicitud(solicitud.id))
        assertEquals(1, repo.obtenerSolicitudes().size)
        assertEquals(EstadoEquipo.RESERVADO, repo.obtenerEquipo(2)?.estado)
    }
}
