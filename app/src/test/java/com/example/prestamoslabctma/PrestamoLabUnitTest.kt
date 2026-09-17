package com.example.prestamoslabctma

import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.repository.InMemoryPrestamoRepository
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/** Suite unitaria trazable con reglas RN-01 a RN-08 y casos TC-04 a TC-16. */
class PrestamoLabUnitTest {
    private lateinit var repo: InMemoryPrestamoRepository
    private lateinit var vm: PrestamoViewModel
    @Before fun preparar(){ repo=InMemoryPrestamoRepository(); vm=PrestamoViewModel(repo) }
    private fun crear(id:Int=1, proposito:String="Uso para práctica técnica", duracion:Int=2)=repo.crearSolicitud(id,"Ambiente 301",proposito,duracion)

    @Test fun tc01_catalogoTieneDatos(){ assertTrue(repo.equipos.value.isNotEmpty()) }
    @Test fun tc02_equipoIdValido(){ assertEquals("Multímetro digital",repo.obtenerEquipo(1)?.nombre) }
    @Test fun tc03_equipoIdInexistente(){ assertNull(repo.obtenerEquipo(999)) }
    @Test fun tc04_proposito9Rechazado(){ assertFalse(vm.validar("A","123456789","2").esValida) }
    @Test fun tc05_proposito10Aceptado(){ assertTrue(vm.validar("A","1234567890","2").esValida) }
    @Test fun tc06_proposito180Aceptado(){ assertTrue(vm.validar("A","x".repeat(180),"2").esValida) }
    @Test fun tc07_proposito181Rechazado(){ assertFalse(vm.validar("A","x".repeat(181),"2").esValida) }
    @Test fun tc08_duracion0Rechazada(){ assertFalse(vm.validar("A","1234567890","0").esValida) }
    @Test fun tc09_duracion1Valida(){ assertTrue(vm.validar("A","1234567890","1").esValida) }
    @Test fun tc10_duracion8Valida(){ assertTrue(vm.validar("A","1234567890","8").esValida) }
    @Test fun tc11_duracion9Rechazada(){ assertFalse(vm.validar("A","1234567890","9").esValida) }
    @Test fun tc12_equipoNoDisponibleRechazado(){ assertTrue(crear(5).isFailure) }
    @Test fun tc13_dobleGuardadoNoDuplica(){ assertTrue(crear().isSuccess); assertTrue(crear().isFailure); assertEquals(1,repo.solicitudes.value.size) }
    @Test fun tc14_crearReservaEquipo(){ val r=crear(); assertTrue(r.isSuccess); assertEquals(EstadoSolicitud.SOLICITADA,r.getOrNull()?.estado); assertEquals(EstadoEquipo.RESERVADO,repo.obtenerEquipo(1)?.estado) }
    @Test fun tc15_cancelarSolicitadaLiberaEquipo(){ val id=crear().getOrThrow().id; assertTrue(repo.cancelarSolicitud(id).isSuccess); assertEquals(EstadoSolicitud.CANCELADA,repo.obtenerSolicitud(id)?.estado); assertEquals(EstadoEquipo.DISPONIBLE,repo.obtenerEquipo(1)?.estado) }
    @Test fun tc16_cancelarCanceladaNoPermitido(){ val id=crear().getOrThrow().id; repo.cancelarSolicitud(id); assertTrue(repo.cancelarSolicitud(id).isFailure) }
    @Test fun tc19_ambienteVacioRechazado(){ assertFalse(vm.validar(" ","1234567890","2").esValida) }
    @Test fun tc20_solicitudIdInexistente(){ assertNull(repo.obtenerSolicitud(999)) }
}
