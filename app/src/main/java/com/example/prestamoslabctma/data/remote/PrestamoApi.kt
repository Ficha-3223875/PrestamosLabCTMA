package com.example.prestamoslabctma.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class EquipoDto(val id: Int, val nombre: String, val categoria: String, val estado: String)
data class SolicitudDto(val id: Int, val equipoId: Int, val estado: String, val ambienteDestino: String, val proposito: String, val duracionHoras: Int)
data class DevolucionDto(val solicitudId: Int, val evidenciaUri: String, val fecha: Long)

interface PrestamoApi {
    @GET("equipos") suspend fun equipos(): List<EquipoDto>
    @POST("solicitudes") suspend fun enviarSolicitud(@Body solicitud: SolicitudDto): SolicitudDto
    @POST("devoluciones") suspend fun enviarDevolucion(@Body devolucion: DevolucionDto)
}
