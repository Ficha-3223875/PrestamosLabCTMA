package com.example.prestamolab_ctma.data.repository
import com.example.prestamolab_ctma.data.local.*
import com.example.prestamolab_ctma.data.remote.PrestamoApi
import com.example.prestamolab_ctma.data.remote.toDomain
import com.example.prestamolab_ctma.data.remote.CrearSolicitudDto
import com.example.prestamolab_ctma.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
class RoomPrestamoRepository(private val db:PrestamoDatabase,private val api:PrestamoApi):PrestamoRepository{
 private val dao=db.prestamoDao()
 override suspend fun inicializar(){if(dao.contarEquipos()==0)dao.guardarEquipos(listOf(
  Equipo(1,"Multímetro digital",CategoriaEquipo.ELECTRONICA,EstadoEquipo.DISPONIBLE,"Instrumento para medir voltaje, corriente y resistencia.").toEntity(),
  Equipo(2,"Kit de electrónica",CategoriaEquipo.ELECTRONICA,EstadoEquipo.DISPONIBLE,"Kit para prácticas básicas de circuitos.").toEntity(),
  Equipo(3,"Tableta Android",CategoriaEquipo.TECNOLOGIA,EstadoEquipo.RESERVADO,"Tableta para actividades de formación.").toEntity(),
  Equipo(4,"Cámara digital",CategoriaEquipo.AUDIOVISUAL,EstadoEquipo.DISPONIBLE,"Cámara para registro de actividades.").toEntity(),
  Equipo(5,"Taladro eléctrico",CategoriaEquipo.HERRAMIENTAS,EstadoEquipo.PRESTADO,"Herramienta eléctrica para prácticas.").toEntity()))}
 override fun observarEquipos():Flow<List<Equipo>> =dao.observarEquipos().map{it.map(EquipoEntity::toDomain)}
 override fun observarSolicitudes():Flow<List<SolicitudPrestamo>> =dao.observarSolicitudes().map{it.map(SolicitudEntity::toDomain)}
 override suspend fun obtenerEquipo(id:Int)=dao.obtenerEquipo(id)?.toDomain()
 override suspend fun obtenerSolicitud(id:Int)=dao.obtenerSolicitud(id)?.toDomain()
 override suspend fun crearSolicitud(equipoId:Int,destino:String,proposito:String,duracionHoras:Int):Result<SolicitudPrestamo>{
  val equipo=dao.obtenerEquipo(equipoId)?.toDomain()?:return Result.failure(IllegalArgumentException("El equipo no existe."))
  if(equipo.estado!=EstadoEquipo.DISPONIBLE)return Result.failure(IllegalStateException("El equipo no está disponible."))
  if(!destinoValido(destino))return Result.failure(IllegalArgumentException("El destino es obligatorio."))
  if(!propositoValido(proposito))return Result.failure(IllegalArgumentException("El propósito debe tener entre 10 y 180 caracteres."))
  if(!duracionValida(duracionHoras))return Result.failure(IllegalArgumentException("La duración debe estar entre 1 y 8 horas."))
  val solicitud=SolicitudPrestamo(dao.maxSolicitudId()+1,equipoId,destino.trim(),proposito.trim(),duracionHoras,EstadoSolicitud.SOLICITADA)
  dao.guardarSolicitud(solicitud.toEntity()); dao.actualizarEquipo(equipo.toEntity().copy(estado=EstadoEquipo.RESERVADO.name)); return Result.success(solicitud)
 }
 override suspend fun cancelarSolicitud(id:Int):Result<Unit>{val s=dao.obtenerSolicitud(id)?.toDomain()?:return Result.failure(IllegalArgumentException("La solicitud no existe."));if(s.estado!=EstadoSolicitud.SOLICITADA)return Result.failure(IllegalStateException("Solo se pueden cancelar solicitudes SOLICITADAS."));dao.actualizarSolicitud(s.copy(estado=EstadoSolicitud.CANCELADA).toEntity());dao.obtenerEquipo(s.equipoId)?.let{dao.actualizarEquipo(it.copy(estado=EstadoEquipo.DISPONIBLE.name))};return Result.success(Unit)}
 override suspend fun guardarEvidencia(id:Int,uri:String,nombre:String):Result<Unit>{val s=dao.obtenerSolicitud(id)?.toDomain()?:return Result.failure(IllegalArgumentException("La solicitud no existe."));dao.actualizarSolicitud(s.copy(evidenciaUri=uri,evidenciaNombre=nombre).toEntity());return Result.success(Unit)}
 override suspend fun guardarUbicacion(id:Int,latitud:Double,longitud:Double):Result<Unit>{val s=dao.obtenerSolicitud(id)?.toDomain()?:return Result.failure(IllegalArgumentException("La solicitud no existe."));dao.actualizarSolicitud(s.copy(latitud=latitud,longitud=longitud).toEntity());return Result.success(Unit)}
 override suspend fun sincronizar(): Result<Unit> = runCatching{api.obtenerEquipos().map{it.toDomain().toEntity()}.let{if(it.isNotEmpty())dao.guardarEquipos(it)}}
}
