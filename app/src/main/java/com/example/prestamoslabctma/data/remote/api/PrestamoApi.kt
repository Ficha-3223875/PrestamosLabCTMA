package com.example.prestamoslabctma.data.remote.api

import com.example.prestamoslabctma.data.remote.dto.CrearSolicitudDto
import com.example.prestamoslabctma.data.remote.dto.DevolucionDto
import com.example.prestamoslabctma.data.remote.dto.EquipoDto
import com.example.prestamoslabctma.data.remote.dto.SolicitudDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PrestamoApi {

    @GET("equipos")
    suspend fun obtenerEquipos(): List<EquipoDto>

    @GET("solicitudes")
    suspend fun obtenerSolicitudes(): List<SolicitudDto>

    @GET("solicitudes/{id}")
    suspend fun obtenerSolicitud(
        @Path("id") id: Int
    ): SolicitudDto

    @POST("solicitudes")
    suspend fun crearSolicitud(
        @Body solicitud: CrearSolicitudDto
    ): SolicitudDto

    @POST("solicitudes/{id}/devolucion")
    suspend fun devolverPrestamo(
        @Path("id") id: Int,
        @Body devolucion: DevolucionDto
    ): SolicitudDto
}