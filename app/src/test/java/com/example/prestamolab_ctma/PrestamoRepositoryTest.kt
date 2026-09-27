package com.example.prestamolab_ctma
import com.example.prestamolab_ctma.data.repository.InMemoryPrestamoRepository
import com.example.prestamolab_ctma.model.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
class PrestamoRepositoryTest{
 @Test fun reglas(){assertFalse(propositoValido("corto"));assertTrue(propositoValido("Realizar práctica de electrónica"));assertFalse(duracionValida(0));assertTrue(duracionValida(8))}
 @Test fun creaSolicitudYReserva()=runTest{val r=InMemoryPrestamoRepository();val x=r.crearSolicitud(1,"Lab 1","Realizar práctica de electrónica",2);assertTrue(x.isSuccess);assertEquals(EstadoEquipo.RESERVADO,r.obtenerEquipo(1)?.estado)}
 @Test fun rechazaNoDisponible()=runTest{val r=InMemoryPrestamoRepository();assertTrue(r.crearSolicitud(1,"Lab","Realizar práctica de electrónica",2).isSuccess);assertTrue(r.crearSolicitud(1,"Lab","Otra práctica válida",2).isFailure)}
}
