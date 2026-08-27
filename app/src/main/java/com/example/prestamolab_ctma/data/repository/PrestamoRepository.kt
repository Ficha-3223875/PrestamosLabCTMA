package com.example.prestamolab_ctma.data.repository

import com.example.prestamolab_ctma.model.Equipo
import com.example.prestamolab_ctma.model.SolicitudPrestamo

interface PrestamoRepository {
    fun obtenerEquipos(): List<Equipo>
    fun obtenerEquipo(equipoId: Int): Equipo?
    fun obtenerSolicitudes(): List<SolicitudPrestamo>
    fun obtenerSolicitud(solicitudId: Int): SolicitudPrestamo?
    fun crearSolicitud(equipoId: Int, destino: String, proposito: String, duracionHoras: Int): Result<SolicitudPrestamo>
    fun cancelarSolicitud(solicitudId: Int): Result<Unit>
}
