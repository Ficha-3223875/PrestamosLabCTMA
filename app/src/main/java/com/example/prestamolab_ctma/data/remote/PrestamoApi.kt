package com.example.prestamolab_ctma.data.remote
import retrofit2.http.*
data class EquipoDto(val id:Int,val nombre:String,val categoria:String,val estado:String,val descripcion:String)
data class SolicitudDto(val id:Int,val equipoId:Int,val ambienteDestino:String,val proposito:String,val duracionHoras:Int,val estado:String)
data class CrearSolicitudDto(val equipoId:Int,val ambienteDestino:String,val proposito:String,val duracionHoras:Int)
interface PrestamoApi{
 @GET("equipos") suspend fun obtenerEquipos():List<EquipoDto>
 @GET("solicitudes/{id}") suspend fun obtenerSolicitud(@Path("id") id:Int):SolicitudDto
 @POST("solicitudes") suspend fun crearSolicitud(@Body body:CrearSolicitudDto):SolicitudDto
}
