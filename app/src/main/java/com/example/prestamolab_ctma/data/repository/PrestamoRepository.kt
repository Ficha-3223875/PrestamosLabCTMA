package com.example.prestamolab_ctma.data.repository
import com.example.prestamolab_ctma.model.*
import kotlinx.coroutines.flow.Flow
interface PrestamoRepository{
 suspend fun inicializar()
 fun observarEquipos():Flow<List<Equipo>>
 fun observarSolicitudes():Flow<List<SolicitudPrestamo>>
 suspend fun obtenerEquipo(id:Int):Equipo?
 suspend fun obtenerSolicitud(id:Int):SolicitudPrestamo?
 suspend fun crearSolicitud(equipoId:Int,destino:String,proposito:String,duracionHoras:Int):Result<SolicitudPrestamo>
 suspend fun cancelarSolicitud(id:Int):Result<Unit>
 suspend fun guardarEvidencia(id:Int,uri:String,nombre:String):Result<Unit>
 suspend fun guardarUbicacion(id:Int,latitud:Double,longitud:Double):Result<Unit>
 suspend fun sincronizar():Result<Unit>
}
