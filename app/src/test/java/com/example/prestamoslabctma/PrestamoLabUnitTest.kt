package com.example.prestamoslabctma

import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.repository.EvidenciaDevolucion
import com.example.prestamoslabctma.repository.InMemoryPrestamoRepository
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/** Suite unitaria trazable con reglas RN-01 a RN-12. */
class PrestamoLabUnitTest {
    private lateinit var repo: InMemoryPrestamoRepository
    private lateinit var vm: PrestamoViewModel
    private val dispatcher = StandardTestDispatcher()
    @Before fun preparar() { Dispatchers.setMain(dispatcher); repo = InMemoryPrestamoRepository(); vm = PrestamoViewModel(repo) }
    @After fun cerrar() { Dispatchers.resetMain() }
    private suspend fun crear(id: Int = 1, proposito: String = "Uso para práctica técnica", duracion: Int = 2) = repo.crearSolicitud(id, "Ambiente 301", proposito, duracion)

    @Test fun tc01_catalogoTieneDatos() = runTest { assertTrue(repo.equipos.value.isNotEmpty()) }
    @Test fun tc02_equipoIdValido() = runTest { assertEquals("Multímetro digital", repo.obtenerEquipo(1)?.nombre) }
    @Test fun tc03_equipoIdInexistente() = runTest { assertNull(repo.obtenerEquipo(999)) }
    @Test fun tc04_proposito9Rechazado() { assertFalse(vm.validar("A", "123456789", "2").esValida) }
    @Test fun tc05_proposito10Aceptado() { assertTrue(vm.validar("A", "1234567890", "2").esValida) }
    @Test fun tc06_proposito180Aceptado() { assertTrue(vm.validar("A", "x".repeat(180), "2").esValida) }
    @Test fun tc07_proposito181Rechazado() { assertFalse(vm.validar("A", "x".repeat(181), "2").esValida) }
    @Test fun tc08_duracion0Rechazada() { assertFalse(vm.validar("A", "1234567890", "0").esValida) }
    @Test fun tc09_duracion1Valida() { assertTrue(vm.validar("A", "1234567890", "1").esValida) }
    @Test fun tc10_duracion8Valida() { assertTrue(vm.validar("A", "1234567890", "8").esValida) }
    @Test fun tc11_duracion9Rechazada() { assertFalse(vm.validar("A", "1234567890", "9").esValida) }
    @Test fun tc12_equipoNoDisponibleRechazado() = runTest { assertTrue(crear(5).isFailure) }
    @Test fun tc13_dobleGuardadoNoDuplica() = runTest { assertTrue(crear().isSuccess); assertTrue(crear().isFailure); assertEquals(1, repo.solicitudes.value.size) }
    @Test fun tc14_crearReservaEquipo() = runTest { val r = crear(); assertTrue(r.isSuccess); assertEquals(EstadoEquipo.RESERVADO, repo.obtenerEquipo(1)?.estado) }
    @Test fun tc15_cancelarSolicitadaLiberaEquipo() = runTest { val id = crear().getOrThrow().id; assertTrue(repo.cancelarSolicitud(id).isSuccess); assertEquals(EstadoSolicitud.CANCELADA, repo.obtenerSolicitud(id)?.estado); assertEquals(EstadoEquipo.DISPONIBLE, repo.obtenerEquipo(1)?.estado) }
    @Test fun tc16_cancelarCanceladaNoPermitido() = runTest { val id = crear().getOrThrow().id; repo.cancelarSolicitud(id); assertTrue(repo.cancelarSolicitud(id).isFailure) }
    @Test fun tc19_ambienteVacioRechazado() { assertFalse(vm.validar(" ", "1234567890", "2").esValida) }
    @Test fun tc20_solicitudIdInexistente() = runTest { assertNull(repo.obtenerSolicitud(999)) }
    @Test fun tc21_entregaCambiaEstado() = runTest { val id = crear().getOrThrow().id; assertTrue(repo.registrarEntrega(id).isSuccess); assertEquals(EstadoSolicitud.ENTREGADA, repo.obtenerSolicitud(id)?.estado); assertEquals(EstadoEquipo.PRESTADO, repo.obtenerEquipo(1)?.estado) }
    @Test fun tc22_devolucionRequiereEntrega() = runTest { val id = crear().getOrThrow().id; assertTrue(repo.registrarDevolucion(id, EvidenciaDevolucion("content://foto", "foto.jpg", "image/jpeg", true)).isFailure) }
    @Test fun tc23_devolucionLiberaEquipo() = runTest { val id = crear().getOrThrow().id; repo.registrarEntrega(id); assertTrue(repo.registrarDevolucion(id, EvidenciaDevolucion("content://foto", "foto.jpg", "image/jpeg", true)).isSuccess); assertEquals(EstadoSolicitud.DEVUELTA, repo.obtenerSolicitud(id)?.estado); assertEquals(EstadoEquipo.DISPONIBLE, repo.obtenerEquipo(1)?.estado) }
    @Test fun tc24_devolucionExigeEvidencia() = runTest { val id = crear().getOrThrow().id; repo.registrarEntrega(id); assertTrue(repo.registrarDevolucion(id, EvidenciaDevolucion("", "", "", false)).isFailure) }
}
